package cz.nuc.wheelgo.service;

import org.jinouts.jws.WebMethod;
import org.jinouts.jws.WebParam;
import org.jinouts.jws.WebResult;
import org.jinouts.jws.WebService;
import org.jinouts.xml.bind.annotation.XmlSeeAlso;
import org.jinouts.xml.ws.Action;
import org.jinouts.xml.ws.RequestWrapper;
import org.jinouts.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2013-04-17T17:26:18.720+02:00
 * Generated source version: 2.6.5
 * 
 */
@WebService(targetNamespace = "http://service/", name = "WheelGoService")
@XmlSeeAlso({ObjectFactory.class})
public interface WheelGoService {

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/getImageRequest", output = "http://service/WheelGoService/getImageResponse")
    @RequestWrapper(localName = "getImage", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetImage")
    @WebMethod
    @ResponseWrapper(localName = "getImageResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetImageResponse")
    public cz.nuc.wheelgo.service.PhotoDTO getImage(
            @WebParam(name = "arg0", targetNamespace = "")
            int arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/addProblemRequest", output = "http://service/WheelGoService/addProblemResponse")
    @RequestWrapper(localName = "addProblem", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.AddProblem")
    @WebMethod
    @ResponseWrapper(localName = "addProblemResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.AddProblemResponse")
    public Integer addProblem(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.ProblemDTO arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/editPlaceRequest", output = "http://service/WheelGoService/editPlaceResponse")
    @RequestWrapper(localName = "editPlace", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.EditPlace")
    @WebMethod
    @ResponseWrapper(localName = "editPlaceResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.EditPlaceResponse")
    public Integer editPlace(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.PlaceDTO arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/navigateRequest", output = "http://service/WheelGoService/navigateResponse")
    @RequestWrapper(localName = "navigate", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.Navigate")
    @WebMethod
    @ResponseWrapper(localName = "navigateResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.NavigateResponse")
    public java.util.List<cz.nuc.wheelgo.service.Node> navigate(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.Location arg0,
            @WebParam(name = "arg1", targetNamespace = "")
            cz.nuc.wheelgo.service.Location arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/getPlaceDetailRequest", output = "http://service/WheelGoService/getPlaceDetailResponse")
    @RequestWrapper(localName = "getPlaceDetail", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetPlaceDetail")
    @WebMethod
    @ResponseWrapper(localName = "getPlaceDetailResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetPlaceDetailResponse")
    public cz.nuc.wheelgo.service.PlaceDTO getPlaceDetail(
            @WebParam(name = "arg0", targetNamespace = "")
            Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/addPlaceRequest", output = "http://service/WheelGoService/addPlaceResponse")
    @RequestWrapper(localName = "addPlace", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.AddPlace")
    @WebMethod
    @ResponseWrapper(localName = "addPlaceResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.AddPlaceResponse")
    public Integer addPlace(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.PlaceDTO arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/getTipDetailRequest", output = "http://service/WheelGoService/getTipDetailResponse")
    @RequestWrapper(localName = "getTipDetail", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetTipDetail")
    @WebMethod
    @ResponseWrapper(localName = "getTipDetailResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetTipDetailResponse")
    public cz.nuc.wheelgo.service.TipDTO getTipDetail(
            @WebParam(name = "arg0", targetNamespace = "")
            Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/getReportRequest", output = "http://service/WheelGoService/getReportResponse")
    @RequestWrapper(localName = "getReport", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetReport")
    @WebMethod
    @ResponseWrapper(localName = "getReportResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetReportResponse")
    public cz.nuc.wheelgo.service.ReportDTO getReport(
            @WebParam(name = "arg0", targetNamespace = "")
            Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/addTipRequest", output = "http://service/WheelGoService/addTipResponse")
    @RequestWrapper(localName = "addTip", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.AddTip")
    @WebMethod
    @ResponseWrapper(localName = "addTipResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.AddTipResponse")
    public Integer addTip(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.TipDTO arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/editTipRequest", output = "http://service/WheelGoService/editTipResponse")
    @RequestWrapper(localName = "editTip", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.EditTip")
    @WebMethod
    @ResponseWrapper(localName = "editTipResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.EditTipResponse")
    public Integer editTip(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.TipDTO arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/getProblemDetailRequest", output = "http://service/WheelGoService/getProblemDetailResponse")
    @RequestWrapper(localName = "getProblemDetail", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetProblemDetail")
    @WebMethod
    @ResponseWrapper(localName = "getProblemDetailResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetProblemDetailResponse")
    public cz.nuc.wheelgo.service.ProblemDTO getProblemDetail(
            @WebParam(name = "arg0", targetNamespace = "")
            Integer arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/deleteSpotRequest", output = "http://service/WheelGoService/deleteSpotResponse")
    @RequestWrapper(localName = "deleteSpot", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.DeleteSpot")
    @WebMethod
    @ResponseWrapper(localName = "deleteSpotResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.DeleteSpotResponse")
    public Integer deleteSpot(
            @WebParam(name = "arg0", targetNamespace = "")
            int arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/editProblemRequest", output = "http://service/WheelGoService/editProblemResponse")
    @RequestWrapper(localName = "editProblem", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.EditProblem")
    @WebMethod
    @ResponseWrapper(localName = "editProblemResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.EditProblemResponse")
    public Integer editProblem(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.ProblemDTO arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @Action(input = "http://service/WheelGoService/getReportsAroundRequest", output = "http://service/WheelGoService/getReportsAroundResponse")
    @RequestWrapper(localName = "getReportsAround", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetReportsAround")
    @WebMethod
    @ResponseWrapper(localName = "getReportsAroundResponse", targetNamespace = "http://service/", className = "cz.nuc.wheelgo.service.GetReportsAroundResponse")
    public java.util.List<cz.nuc.wheelgo.service.ReportDTO> getReportsAround(
            @WebParam(name = "arg0", targetNamespace = "")
            cz.nuc.wheelgo.service.Location arg0,
            @WebParam(name = "arg1", targetNamespace = "")
            double arg1
    );
}
