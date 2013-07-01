package com.minervasforge.velocity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Persister implements Serializable {
    ObjectOutputStream out;

    Persister(ObjectOutputStream outputStream) {
        this.out = outputStream;
    }

    public void store(TaskRepository taskRepository, PointWallet pointWallet,
                      RewardRepository rewardRepository, SkillRepository skillRepository) {
        try {
            out.writeObject(taskRepository);
            out.writeObject(pointWallet);
            out.writeObject(rewardRepository);
            out.writeObject(skillRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }// finally {
//			try {
//				out.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
    }


    public Object load(ObjectInputStream objectInputStream, Class objectClass) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return objectInputStream.readObject();
    }


}