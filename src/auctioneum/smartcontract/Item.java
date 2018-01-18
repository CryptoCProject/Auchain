/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcontract;

/**
 *
 * @author panagiotisspentzouris
 */
public class Item {
    private int id;
    private String name;
    private String owner;//adress of owner

    public Item(int id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
}
