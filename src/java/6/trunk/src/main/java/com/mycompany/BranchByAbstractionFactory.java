package com.mycompany;

/**
 * @author Paul Hammant
 */
public abstract class BranchByAbstractionFactory {

    abstract String getHairColor();

    static BranchByAbstractionFactory make(String name){
        try {
            return (BranchByAbstractionFactory) Class.forName("com.mycompany." + name).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
