/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.woehlke.javaee7.petclinic.web;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.woehlke.javaee7.petclinic.dao.VetDao;
import org.woehlke.javaee7.petclinic.dao.VisitDao;
import org.woehlke.javaee7.petclinic.entities.Owner;
import org.woehlke.javaee7.petclinic.entities.Pet;
import org.woehlke.javaee7.petclinic.entities.Vet;
import org.woehlke.javaee7.petclinic.entities.Visit;

/**
 *
 * @author Luiz Armando
 */
@ManagedBean
@SessionScoped
public class VisitController implements Serializable{

    @EJB
    private VisitDao visitDao;
    
    @EJB
    private VetDao vetDao;

    private List<Owner> ownerList;

    private Owner owner;
      
    private List<Visit> visitList;
    
    private Date visitDate;

    private Pet pet;

    private long petTypeId;
    
    private Visit visit;
    
    private long vetId;
    
    private String vetName;
    
    private Vet vet;
    private List<Vet> vetList;
    
    private int scrollerPage;
    
    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }
    
    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
    
    public String getVetName(){
        return vetName;
    }
    
    public void setVetName(String vetName){
        this.vetName = vetName;
    }
    
    public List<Visit> getVisitList(){
        return visitList;
    }
    
    public List<Vet> getVetList(){
        return vetList;
    }

    public long getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(long petTypeId) {
        this.petTypeId = petTypeId;
    }
    
    public long getVetId(){
        return vetId;
    }
    
    public void setVetId(long vetId){
        this.vetId = vetId;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }
    
    public String searchVisits(){
        if(visitDate == null && vetName.isEmpty()){
            this.visitList = visitDao.getAll();
        }
        else{
            this.visitList = visitDao.getAll();
            Iterator<Visit> i = visitList.iterator();
            while(i.hasNext()){
                this.visit = i.next();
                if(visitDate != null){
                    if(visit.getDate().compareTo(this.visitDate) != 0){
                        i.remove();
                    }
                }
                else if(!vetName.isEmpty()){
                    try{
                        vetList = vetDao.search(vetName);
                    }
                    catch(Exception e){
                        vetList = null;
                    }
                    if(vetList != null)
                    {
                        Iterator<Vet> j = vetList.iterator();
                        while(j.hasNext()){
                            this.vet = j.next();
                            if(!visit.getVet().getId().equals(vet.getId())){
                                i.remove();
                            }
                        }
                    }
                }
                
            }
        }
        return "visits.jsf";
    }
    
    public void setScrollerPage(int scrollerPage) {
        this.scrollerPage = scrollerPage;
    }

    public int getScrollerPage() {
        return scrollerPage;
    }
}
