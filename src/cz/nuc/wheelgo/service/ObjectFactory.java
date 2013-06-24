
package cz.nuc.wheelgo.service;

import org.jinouts.xml.bind.JAXBElement;
import org.jinouts.xml.bind.annotation.XmlElementDecl;
import org.jinouts.xml.bind.annotation.XmlRegistry;
import org.jinouts.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetReport_QNAME = new QName("http://service/", "getReport");
    private final static QName _EditTipResponse_QNAME = new QName("http://service/", "editTipResponse");
    private final static QName _GetProblemDetailResponse_QNAME = new QName("http://service/", "getProblemDetailResponse");
    private final static QName _EditProblem_QNAME = new QName("http://service/", "editProblem");
    private final static QName _DeleteSpotResponse_QNAME = new QName("http://service/", "deleteSpotResponse");
    private final static QName _NavigateResponse_QNAME = new QName("http://service/", "navigateResponse");
    private final static QName _EditTip_QNAME = new QName("http://service/", "editTip");
    private final static QName _AddTipResponse_QNAME = new QName("http://service/", "addTipResponse");
    private final static QName _GetReportsAroundResponse_QNAME = new QName("http://service/", "getReportsAroundResponse");
    private final static QName _GetPlaceDetailResponse_QNAME = new QName("http://service/", "getPlaceDetailResponse");
    private final static QName _GetProblemDetail_QNAME = new QName("http://service/", "getProblemDetail");
    private final static QName _AddTip_QNAME = new QName("http://service/", "addTip");
    private final static QName _AddProblem_QNAME = new QName("http://service/", "addProblem");
    private final static QName _EditPlaceResponse_QNAME = new QName("http://service/", "editPlaceResponse");
    private final static QName _DeleteSpot_QNAME = new QName("http://service/", "deleteSpot");
    private final static QName _GetImage_QNAME = new QName("http://service/", "getImage");
    private final static QName _AddPlaceResponse_QNAME = new QName("http://service/", "addPlaceResponse");
    private final static QName _GetReportsAround_QNAME = new QName("http://service/", "getReportsAround");
    private final static QName _GetTipDetailResponse_QNAME = new QName("http://service/", "getTipDetailResponse");
    private final static QName _AddPlace_QNAME = new QName("http://service/", "addPlace");
    private final static QName _GetImageResponse_QNAME = new QName("http://service/", "getImageResponse");
    private final static QName _EditProblemResponse_QNAME = new QName("http://service/", "editProblemResponse");
    private final static QName _AddProblemResponse_QNAME = new QName("http://service/", "addProblemResponse");
    private final static QName _Navigate_QNAME = new QName("http://service/", "navigate");
    private final static QName _EditPlace_QNAME = new QName("http://service/", "editPlace");
    private final static QName _GetTipDetail_QNAME = new QName("http://service/", "getTipDetail");
    private final static QName _GetPlaceDetail_QNAME = new QName("http://service/", "getPlaceDetail");
    private final static QName _GetReportResponse_QNAME = new QName("http://service/", "getReportResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EditPlaceResponse }
     * 
     */
    public EditPlaceResponse createEditPlaceResponse() {
        return new EditPlaceResponse();
    }

    /**
     * Create an instance of {@link AddProblem }
     * 
     */
    public AddProblem createAddProblem() {
        return new AddProblem();
    }

    /**
     * Create an instance of {@link AddTip }
     * 
     */
    public AddTip createAddTip() {
        return new AddTip();
    }

    /**
     * Create an instance of {@link GetProblemDetail }
     * 
     */
    public GetProblemDetail createGetProblemDetail() {
        return new GetProblemDetail();
    }

    /**
     * Create an instance of {@link GetPlaceDetailResponse }
     * 
     */
    public GetPlaceDetailResponse createGetPlaceDetailResponse() {
        return new GetPlaceDetailResponse();
    }

    /**
     * Create an instance of {@link GetReportsAroundResponse }
     * 
     */
    public GetReportsAroundResponse createGetReportsAroundResponse() {
        return new GetReportsAroundResponse();
    }

    /**
     * Create an instance of {@link AddTipResponse }
     * 
     */
    public AddTipResponse createAddTipResponse() {
        return new AddTipResponse();
    }

    /**
     * Create an instance of {@link EditTip }
     * 
     */
    public EditTip createEditTip() {
        return new EditTip();
    }

    /**
     * Create an instance of {@link NavigateResponse }
     * 
     */
    public NavigateResponse createNavigateResponse() {
        return new NavigateResponse();
    }

    /**
     * Create an instance of {@link DeleteSpotResponse }
     * 
     */
    public DeleteSpotResponse createDeleteSpotResponse() {
        return new DeleteSpotResponse();
    }

    /**
     * Create an instance of {@link EditProblem }
     * 
     */
    public EditProblem createEditProblem() {
        return new EditProblem();
    }

    /**
     * Create an instance of {@link EditTipResponse }
     * 
     */
    public EditTipResponse createEditTipResponse() {
        return new EditTipResponse();
    }

    /**
     * Create an instance of {@link GetProblemDetailResponse }
     * 
     */
    public GetProblemDetailResponse createGetProblemDetailResponse() {
        return new GetProblemDetailResponse();
    }

    /**
     * Create an instance of {@link GetReport }
     * 
     */
    public GetReport createGetReport() {
        return new GetReport();
    }

    /**
     * Create an instance of {@link GetReportResponse }
     * 
     */
    public GetReportResponse createGetReportResponse() {
        return new GetReportResponse();
    }

    /**
     * Create an instance of {@link GetPlaceDetail }
     * 
     */
    public GetPlaceDetail createGetPlaceDetail() {
        return new GetPlaceDetail();
    }

    /**
     * Create an instance of {@link GetTipDetail }
     * 
     */
    public GetTipDetail createGetTipDetail() {
        return new GetTipDetail();
    }

    /**
     * Create an instance of {@link EditPlace }
     * 
     */
    public EditPlace createEditPlace() {
        return new EditPlace();
    }

    /**
     * Create an instance of {@link Navigate }
     * 
     */
    public Navigate createNavigate() {
        return new Navigate();
    }

    /**
     * Create an instance of {@link EditProblemResponse }
     * 
     */
    public EditProblemResponse createEditProblemResponse() {
        return new EditProblemResponse();
    }

    /**
     * Create an instance of {@link AddProblemResponse }
     * 
     */
    public AddProblemResponse createAddProblemResponse() {
        return new AddProblemResponse();
    }

    /**
     * Create an instance of {@link GetImageResponse }
     * 
     */
    public GetImageResponse createGetImageResponse() {
        return new GetImageResponse();
    }

    /**
     * Create an instance of {@link AddPlace }
     * 
     */
    public AddPlace createAddPlace() {
        return new AddPlace();
    }

    /**
     * Create an instance of {@link GetTipDetailResponse }
     * 
     */
    public GetTipDetailResponse createGetTipDetailResponse() {
        return new GetTipDetailResponse();
    }

    /**
     * Create an instance of {@link GetReportsAround }
     * 
     */
    public GetReportsAround createGetReportsAround() {
        return new GetReportsAround();
    }

    /**
     * Create an instance of {@link AddPlaceResponse }
     * 
     */
    public AddPlaceResponse createAddPlaceResponse() {
        return new AddPlaceResponse();
    }

    /**
     * Create an instance of {@link GetImage }
     * 
     */
    public GetImage createGetImage() {
        return new GetImage();
    }

    /**
     * Create an instance of {@link DeleteSpot }
     * 
     */
    public DeleteSpot createDeleteSpot() {
        return new DeleteSpot();
    }

    /**
     * Create an instance of {@link PlaceDTO }
     * 
     */
    public PlaceDTO createPlaceDTO() {
        return new PlaceDTO();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link ReportDTO }
     * 
     */
    public ReportDTO createReportDTO() {
        return new ReportDTO();
    }

    /**
     * Create an instance of {@link ProblemDTO }
     * 
     */
    public ProblemDTO createProblemDTO() {
        return new ProblemDTO();
    }

    /**
     * Create an instance of {@link TipDTO }
     * 
     */
    public TipDTO createTipDTO() {
        return new TipDTO();
    }

    /**
     * Create an instance of {@link PhotoDTO }
     * 
     */
    public PhotoDTO createPhotoDTO() {
        return new PhotoDTO();
    }

    /**
     * Create an instance of {@link Node }
     * 
     */
    public Node createNode() {
        return new Node();
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getReport")
    public JAXBElement<GetReport> createGetReport(GetReport value) {
        return new JAXBElement<GetReport>(_GetReport_QNAME, GetReport.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link EditTipResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "editTipResponse")
    public JAXBElement<EditTipResponse> createEditTipResponse(EditTipResponse value) {
        return new JAXBElement<EditTipResponse>(_EditTipResponse_QNAME, EditTipResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetProblemDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getProblemDetailResponse")
    public JAXBElement<GetProblemDetailResponse> createGetProblemDetailResponse(GetProblemDetailResponse value) {
        return new JAXBElement<GetProblemDetailResponse>(_GetProblemDetailResponse_QNAME, GetProblemDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link EditProblem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "editProblem")
    public JAXBElement<EditProblem> createEditProblem(EditProblem value) {
        return new JAXBElement<EditProblem>(_EditProblem_QNAME, EditProblem.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link DeleteSpotResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "deleteSpotResponse")
    public JAXBElement<DeleteSpotResponse> createDeleteSpotResponse(DeleteSpotResponse value) {
        return new JAXBElement<DeleteSpotResponse>(_DeleteSpotResponse_QNAME, DeleteSpotResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link NavigateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "navigateResponse")
    public JAXBElement<NavigateResponse> createNavigateResponse(NavigateResponse value) {
        return new JAXBElement<NavigateResponse>(_NavigateResponse_QNAME, NavigateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link EditTip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "editTip")
    public JAXBElement<EditTip> createEditTip(EditTip value) {
        return new JAXBElement<EditTip>(_EditTip_QNAME, EditTip.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link AddTipResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "addTipResponse")
    public JAXBElement<AddTipResponse> createAddTipResponse(AddTipResponse value) {
        return new JAXBElement<AddTipResponse>(_AddTipResponse_QNAME, AddTipResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetReportsAroundResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getReportsAroundResponse")
    public JAXBElement<GetReportsAroundResponse> createGetReportsAroundResponse(GetReportsAroundResponse value) {
        return new JAXBElement<GetReportsAroundResponse>(_GetReportsAroundResponse_QNAME, GetReportsAroundResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetPlaceDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getPlaceDetailResponse")
    public JAXBElement<GetPlaceDetailResponse> createGetPlaceDetailResponse(GetPlaceDetailResponse value) {
        return new JAXBElement<GetPlaceDetailResponse>(_GetPlaceDetailResponse_QNAME, GetPlaceDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetProblemDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getProblemDetail")
    public JAXBElement<GetProblemDetail> createGetProblemDetail(GetProblemDetail value) {
        return new JAXBElement<GetProblemDetail>(_GetProblemDetail_QNAME, GetProblemDetail.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link AddTip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "addTip")
    public JAXBElement<AddTip> createAddTip(AddTip value) {
        return new JAXBElement<AddTip>(_AddTip_QNAME, AddTip.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link AddProblem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "addProblem")
    public JAXBElement<AddProblem> createAddProblem(AddProblem value) {
        return new JAXBElement<AddProblem>(_AddProblem_QNAME, AddProblem.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link EditPlaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "editPlaceResponse")
    public JAXBElement<EditPlaceResponse> createEditPlaceResponse(EditPlaceResponse value) {
        return new JAXBElement<EditPlaceResponse>(_EditPlaceResponse_QNAME, EditPlaceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link DeleteSpot }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "deleteSpot")
    public JAXBElement<DeleteSpot> createDeleteSpot(DeleteSpot value) {
        return new JAXBElement<DeleteSpot>(_DeleteSpot_QNAME, DeleteSpot.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetImage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getImage")
    public JAXBElement<GetImage> createGetImage(GetImage value) {
        return new JAXBElement<GetImage>(_GetImage_QNAME, GetImage.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link AddPlaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "addPlaceResponse")
    public JAXBElement<AddPlaceResponse> createAddPlaceResponse(AddPlaceResponse value) {
        return new JAXBElement<AddPlaceResponse>(_AddPlaceResponse_QNAME, AddPlaceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetReportsAround }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getReportsAround")
    public JAXBElement<GetReportsAround> createGetReportsAround(GetReportsAround value) {
        return new JAXBElement<GetReportsAround>(_GetReportsAround_QNAME, GetReportsAround.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetTipDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getTipDetailResponse")
    public JAXBElement<GetTipDetailResponse> createGetTipDetailResponse(GetTipDetailResponse value) {
        return new JAXBElement<GetTipDetailResponse>(_GetTipDetailResponse_QNAME, GetTipDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link AddPlace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "addPlace")
    public JAXBElement<AddPlace> createAddPlace(AddPlace value) {
        return new JAXBElement<AddPlace>(_AddPlace_QNAME, AddPlace.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetImageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getImageResponse")
    public JAXBElement<GetImageResponse> createGetImageResponse(GetImageResponse value) {
        return new JAXBElement<GetImageResponse>(_GetImageResponse_QNAME, GetImageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link EditProblemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "editProblemResponse")
    public JAXBElement<EditProblemResponse> createEditProblemResponse(EditProblemResponse value) {
        return new JAXBElement<EditProblemResponse>(_EditProblemResponse_QNAME, EditProblemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link AddProblemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "addProblemResponse")
    public JAXBElement<AddProblemResponse> createAddProblemResponse(AddProblemResponse value) {
        return new JAXBElement<AddProblemResponse>(_AddProblemResponse_QNAME, AddProblemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link Navigate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "navigate")
    public JAXBElement<Navigate> createNavigate(Navigate value) {
        return new JAXBElement<Navigate>(_Navigate_QNAME, Navigate.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link EditPlace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "editPlace")
    public JAXBElement<EditPlace> createEditPlace(EditPlace value) {
        return new JAXBElement<EditPlace>(_EditPlace_QNAME, EditPlace.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetTipDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getTipDetail")
    public JAXBElement<GetTipDetail> createGetTipDetail(GetTipDetail value) {
        return new JAXBElement<GetTipDetail>(_GetTipDetail_QNAME, GetTipDetail.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetPlaceDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getPlaceDetail")
    public JAXBElement<GetPlaceDetail> createGetPlaceDetail(GetPlaceDetail value) {
        return new JAXBElement<GetPlaceDetail>(_GetPlaceDetail_QNAME, GetPlaceDetail.class, null, value);
    }

    /**
     * Create an instance of {@link org.jinouts.xml.bind.JAXBElement }{@code <}{@link GetReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "getReportResponse")
    public JAXBElement<GetReportResponse> createGetReportResponse(GetReportResponse value) {
        return new JAXBElement<GetReportResponse>(_GetReportResponse_QNAME, GetReportResponse.class, null, value);
    }

}
