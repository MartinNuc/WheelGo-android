
package cz.nuc.wheelgo.service;

import org.jinouts.xml.bind.annotation.XmlAccessType;
import org.jinouts.xml.bind.annotation.XmlAccessorType;
import org.jinouts.xml.bind.annotation.XmlType;


/**
 * <p>Java class for problemDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="problemDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service/}reportDTO">
 *       &lt;sequence>
 *         &lt;element name="expiration" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "problemDTO", propOrder = {
    "expiration"
})
public class ProblemDTO
    extends ReportDTO
{

    protected long expiration;

    /**
     * Gets the value of the expiration property.
     * 
     */
    public long getExpiration() {
        return expiration;
    }

    /**
     * Sets the value of the expiration property.
     * 
     */
    public void setExpiration(long value) {
        this.expiration = value;
    }

}
