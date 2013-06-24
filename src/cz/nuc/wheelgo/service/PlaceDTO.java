
package cz.nuc.wheelgo.service;

import org.jinouts.xml.bind.annotation.XmlAccessType;
import org.jinouts.xml.bind.annotation.XmlAccessorType;
import org.jinouts.xml.bind.annotation.XmlType;


/**
 * <p>Java class for placeDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="placeDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service/}reportDTO">
 *       &lt;sequence>
 *         &lt;element name="accesibility" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "placeDTO", propOrder = {
    "accesibility"
})
public class PlaceDTO
    extends ReportDTO
{

    protected int accesibility;

    /**
     * Gets the value of the accesibility property.
     * 
     */
    public int getAccesibility() {
        return accesibility;
    }

    /**
     * Sets the value of the accesibility property.
     * 
     */
    public void setAccesibility(int value) {
        this.accesibility = value;
    }

}
