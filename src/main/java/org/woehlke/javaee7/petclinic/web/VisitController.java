/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.woehlke.javaee7.petclinic.web;

/*
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.awt.Desktop;
import java.io.File;
*/

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.text.SimpleDateFormat;
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
    
    private Date visitDateBegin;
    private Date visitDateEnd;

    private Pet pet;

    private long petTypeId;
    
    private Visit visit;
    
    private long vetId;
      
    private Vet vet;
    
    private List<Vet> vetList;
    
    private int scrollerPage;
    
    private String stringDate;
    
    public String getStringDate(){
        return stringDate;
    }
    
    public void setStringDate(String stringDate){
        this.stringDate = stringDate;
    }
    
    public Visit getVisit() {
        return visit;
    }
    
    public Vet getVet(){
        return vet;
    }
    
    public void setVet(Vet vet){
        this.vet = vet;
    }
    
    public void setVisit(Visit visit) {
        this.visit = visit;
    }
    
    public Date getVisitDateEnd() {
        return visitDateEnd;
    }

    public void setVisitDateEnd(Date visitDateEnd) {
        this.visitDateEnd = visitDateEnd;
    }
    
     public Date getVisitDateBegin() {
        return visitDateBegin;
    }

    public void setVisitDateBegin(Date visitDateBegin) {
        this.visitDateBegin = visitDateBegin;
    }
    
    public String getVetName(){
        return vet.getFirstName() + vet.getLastName();
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
    
    public List<Vet> getAllVets(){
        return vetDao.getAll();
    }
    
    public int getVisitTotal(){
        return visitList.size();
    }
    
    public String searchVisits(){
        this.visitList = visitDao.getAll();
        this.vet = vetDao.findById(this.vetId);
        if(this.visitDateBegin instanceof Date){
            this.stringDate = new SimpleDateFormat("yyyy-MM-dd").format(visitDateBegin);
            if(this.visitDateEnd instanceof Date){
                stringDate += " to ";
                stringDate +=  new SimpleDateFormat("yyyy-MM-dd").format(visitDateEnd);
            }            
        }
        else if(this.visitDateEnd instanceof Date){
            stringDate = new SimpleDateFormat("yyyy-MM-dd").format(visitDateEnd);
        }
        Iterator<Visit> i = visitList.iterator();
        while(i.hasNext()){
            this.visit = i.next();
            if(visit.getVet().getId().equals(vet.getId())){
                if(visitDateBegin instanceof Date && visitDateEnd instanceof Date && visitDateBegin.before(visitDateEnd)){
                    if(visit.getDate().before(visitDateBegin) || visit.getDate().after(visitDateEnd)){
                        i.remove();
                    }
                }
                else if(visitDateBegin instanceof Date || visitDateEnd instanceof Date){
                    if(visitDateBegin instanceof Date && !visit.getDate().equals(visitDateBegin)){
                        i.remove();
                    }
                    else if(visitDateEnd instanceof Date && !visit.getDate().equals(visitDateEnd)){
                        i.remove();
                    }
                }
                else{
                    i.remove();
                }
            }
            else{
                i.remove();
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
