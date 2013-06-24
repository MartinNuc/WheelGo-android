package cz.nuc.wheelgo.datasource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import cz.nuc.wheelgo.reportingsystem.*;
import cz.nuc.wheelgo.reportingsystem.Node;
import cz.nuc.wheelgo.service.*;
import cz.nuc.wheelgo.service.Location;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mist
 * Date: 4.4.13
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class DataFromService implements DataSource {

    private WheelGoService service;

    public DataFromService() {
        WheelGoService_Service serviceStub = new WheelGoService_Service();
        service = serviceStub.getWheelGoServicePort();
    }

    @Override
    public List<Spot> getReportsAround(cz.nuc.wheelgo.reportingsystem.Location location, int radius) {
        List<Spot> ret = new LinkedList<Spot>();
        Location l = new Location();
        l.setLatitude(location.getLatitude());
        l.setLongitude(location.getLongitude());
        List<ReportDTO> dto = service.getReportsAround(l, radius);
        for (ReportDTO report : dto) {
            try {
                Spot s = new Spot(report.getIdReport());
                s.setName(report.getName());
                s.setDescribtion(report.getDescribtion());
                s.setCoordinates(new LatLng(report.getLatitude(), report.getLongitude()));
                switch (report.getType()) {
                    case ReportType.PLACE:
                        s.setType(SpotType.PLACE);
                        break;
                    case ReportType.TIP:
                        s.setType(SpotType.TIP);
                        break;
                    case ReportType.PROBLEM:
                        s.setType(SpotType.PROBLEM);
                        break;
                }
                ret.add(s);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.e("Error", e.getMessage());
                continue;
            }
        }
        return ret;
    }

    @Override
    public void addPlace(Place newOne) {
        PlaceDTO placeDto = new PlaceDTO();
        placeDto.setAccesibility(newOne.getAccessibility());
        placeDto.setDeleted(false);
        placeDto.setDescribtion(newOne.getDescription());
        placeDto.setLatitude(newOne.getCoordinates().latitude);
        placeDto.setLongitude(newOne.getCoordinates().longitude);
        placeDto.setName(newOne.getName());
        placeDto.setType(ReportType.PLACE);
        service.addPlace(placeDto);
    }

    @Override
    public void addProblem(Problem newOne) {
        ProblemDTO problemDto = new ProblemDTO();
        problemDto.setType(ReportType.PROBLEM);
        problemDto.setName(newOne.getName());
        problemDto.setDescribtion(newOne.getDescription());
        problemDto.setLatitude(newOne.getCoordinates().latitude);
        problemDto.setLongitude(newOne.getCoordinates().longitude);
        problemDto.setExpiration(newOne.getDuration().getTime());
        service.addProblem(problemDto);
    }

    @Override
    public void addTip(Tip newOne) {
        TipDTO tipDto = new TipDTO();
        tipDto.setLatitude(newOne.getCoordinates().latitude);
        tipDto.setLongitude(newOne.getCoordinates().longitude);
        tipDto.setDescribtion(newOne.getDescription());
        tipDto.setType(ReportType.TIP);
        tipDto.setName(newOne.getName());
        service.addTip(tipDto);
    }


    @Override
    public Bitmap getImage(int id) {
        PhotoDTO ret = service.getImage(id);
        if (ret == null)
            return null;
        byte[] data = ret.getImage();
        if (data == null)
            return null;
        Bitmap bmp;
        bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bmp.copy(Bitmap.Config.ARGB_8888, true);
    }

    @Override
    public void deleteSpot(int id) {
        service.deleteSpot(id);
    }

    @Override
    public void editProblem(Problem edittedOne) {
        ProblemDTO problemDto = new ProblemDTO();
        problemDto.setIdReport(edittedOne.getId());
        problemDto.setType(ReportType.PROBLEM);
        problemDto.setName(edittedOne.getName());
        problemDto.setDescribtion(edittedOne.getDescription());
        problemDto.setLatitude(edittedOne.getCoordinates().latitude);
        problemDto.setLongitude(edittedOne.getCoordinates().longitude);
        problemDto.setExpiration(edittedOne.getDuration().getTime());
        service.editProblem(problemDto);
    }

    @Override
    public void editTip(Tip edittedOne) {
        TipDTO tipDto = new TipDTO();
        tipDto.setIdReport(edittedOne.getId());
        tipDto.setLatitude(edittedOne.getCoordinates().latitude);
        tipDto.setLongitude(edittedOne.getCoordinates().longitude);
        tipDto.setDescribtion(edittedOne.getDescription());
        tipDto.setType(ReportType.TIP);
        tipDto.setName(edittedOne.getName());
        service.editTip(tipDto);
    }

    @Override
    public void editPlace(Place edittedOne) {
        PlaceDTO placeDto = new PlaceDTO();
        placeDto.setIdReport(edittedOne.getId());
        placeDto.setAccesibility(edittedOne.getAccessibility());
        placeDto.setDeleted(false);
        placeDto.setDescribtion(edittedOne.getDescription());
        placeDto.setLatitude(edittedOne.getCoordinates().latitude);
        placeDto.setLongitude(edittedOne.getCoordinates().longitude);
        placeDto.setName(edittedOne.getName());
        placeDto.setType(ReportType.PLACE);
        service.editPlace(placeDto);
    }

    @Override
    public Problem getProblemDetail(int id) {
        Problem ret = new Problem();
        ProblemDTO dto = service.getProblemDetail(id);
        ret.setDuration(dto.getExpiration());
        ret.setName(dto.getName());
        ret.setDescribtion(dto.getDescribtion());
        LatLng loc = new LatLng(dto.getLatitude(), dto.getLongitude());
        ret.setCoordinates(loc);

        return ret;
    }

    @Override
    public Place getPlaceDetail(int id) {
        Place ret = new Place();
        PlaceDTO dto = service.getPlaceDetail(id);
        ret.setPhoto(getImage(id));
        LatLng loc = new LatLng(dto.getLatitude(), dto.getLongitude());
        ret.setCoordinates(loc);
        ret.setDescribtion(dto.getDescribtion());
        ret.setAccesibility(dto.getAccesibility());
        ret.setName(dto.getName());

        return ret;
    }

    @Override
    public Tip getTipDetail(int id) {
        Tip ret = new Tip();
        TipDTO dto = service.getTipDetail(id);
        ret.setPhoto(getImage(id));
        LatLng loc = new LatLng(dto.getLatitude(), dto.getLongitude());
        ret.setCoordinates(loc);
        ret.setDescribtion(dto.getDescribtion());
        ret.setName(dto.getName());

        return ret;

    }

    @Override
    public List<Node> navigate(cz.nuc.wheelgo.reportingsystem.Location from, cz.nuc.wheelgo.reportingsystem.Location to) {
        List<Node> ret = new ArrayList<Node>();

        Location serviceFrom = new Location();
        serviceFrom.setLatitude(from.getLatitude());
        serviceFrom.setLongitude(from.getLongitude());
        Location serviceTo = new Location();
        serviceTo.setLatitude(from.getLatitude());
        serviceTo.setLongitude(from.getLongitude());

        List<cz.nuc.wheelgo.service.Node> result = service.navigate(serviceFrom, serviceTo);

        for(cz.nuc.wheelgo.service.Node node : result)
        {
            Node n = new Node();
            n.setLatitude(node.getLatitude());
            n.setLongitude(node.getLongitude());
            n.setBearing(node.getBearing());
            n.setDescription(node.getDescription());
            ret.add(n);
        }

        return ret;
    }
}
