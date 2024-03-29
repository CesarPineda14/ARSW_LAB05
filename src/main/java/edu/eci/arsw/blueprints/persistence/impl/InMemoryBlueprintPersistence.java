/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service("InMemoryBlueprintPersistence")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        ArrayList<Point> pts=new ArrayList<>(Arrays.asList(new Point(140, 140),new Point(115, 115)));
        ArrayList<Point> pts1=new ArrayList<>(Arrays.asList(new Point(140, 140),new Point(115, 115)));
        ArrayList<Point> pts2=new ArrayList<>(Arrays.asList(new Point(140, 140),new Point(115, 115)));
        Blueprint bp=new Blueprint("Jose", "_bpname_",pts);
        Blueprint bp1=new Blueprint("Jose", "_bpname_1",pts1);
        Blueprint bp2=new Blueprint("Carlos", "_bpname_2",pts2);


        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor (String author) throws BlueprintNotFoundException{
        Set<Blueprint> Author = new HashSet<Blueprint>();
        for (Tuple<String,String> tupla : blueprints.keySet()){
            if(tupla.getElem1().equals(author)){
                Author.add(blueprints.get(tupla));
            }
        }
        return Author;
    }

    @Override
    public Set<Blueprint> getBlueprintByAll() throws BlueprintNotFoundException{
        Set<Blueprint> Author = new HashSet<Blueprint>();
        for(Tuple<String,String> tupla : blueprints.keySet()){
            Author.add(blueprints.get(tupla));
        }
        return Author;
    }
    
}
