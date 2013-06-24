
package cz.nuc.wheelgo.service;

import java.util.ArrayList;
import java.util.List;
import org.jinouts.xml.bind.annotation.XmlAccessType;
import org.jinouts.xml.bind.annotation.XmlAccessorType;
import org.jinouts.xml.bind.annotation.XmlElement;
import org.jinouts.xml.bind.annotation.XmlSeeAlso;
import org.jinouts.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reportDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reportDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="describtion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idReport" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="logsCollection" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="photosCollection" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reportDTO", propOrder = {
    "date",
    "deleted",
    "describtion",
    "idReport",
    "latitude",
    "logsCollection",
    "longitude",
    "name",
    "photosCollection",
    "type"
})
@XmlSeeAlso({
    PlaceDTO.class,
    ProblemDTO.class,
    TipDTO.class
})
public class ReportDTO {

    protected long date;
    protected boolean deleted;
    protected String describtion;
    protected Integer idReport;
    protected double latitude;
    @XmlElement(nillable = true)
    protected List<Integer> logsCollection;
    protected double longitude;
    protected String name;
    @XmlElement(nillable = true)
    protected List<Integer> photosCollection;
    protected int type;

    /**
     * Gets the value of the date property.
     * 
     */
    public long getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     */
    public void setDate(long value) {
        this.date = value;
    }

    /**
     * Gets the value of the deleted property.
     * 
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the value of the deleted property.
     * 
     */
    public void setDeleted(boolean value) {
        this.deleted = value;
    }

    /**
     * Gets the value of the describtion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescribtion() {
        return describtion;
    }

    /**
     * Sets the value of the describtion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescribtion(String value) {
        this.describtion = value;
    }

    /**
     * Gets the value of the idReport property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdReport() {
        return idReport;
    }

    /**
     * Sets the value of the idReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdReport(Integer value) {
        this.idReport = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     */
    public void setLatitude(double value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the logsCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logsCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogsCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getLogsCollection() {
        if (logsCollection == null) {
            logsCollection = new ArrayList<Integer>();
        }
        return this.logsCollection;
    }

    /**
     * Gets the value of the longitude property.
     * 
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     */
    public void setLongitude(double value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the photosCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the photosCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhotosCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getPhotosCollection() {
        if (photosCollection == null) {
            photosCollection = new ArrayList<Integer>();
        }
        return this.photosCollection;
    }

    /**
     * Gets the value of the type property.
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     */
    public void setType(int value) {
        this.type = value;
    }

}
