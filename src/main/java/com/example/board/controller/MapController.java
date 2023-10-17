package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import com.example.board.model.Board;
import com.example.board.model.Point;
import com.example.board.repository.PointRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MapController {
   @Autowired
   PointRepository pointRepository;
   
   @GetMapping("/map")
   public String map() {
      return "map/map";
   }
   @PostMapping("/map")
   public String mapPost(@ModelAttribute Point point){
      pointRepository.save(point);
      return "redirect:/map";
   }

   @GetMapping("/map/search")
   public String search(Model model){
      Sort sort = Sort.by(Order.desc("id"));
      List<Point> points = pointRepository.findAll(sort);
      model.addAttribute("points", points);
      return "map/search";
   }
   @PostMapping("/map/search")
   public String searchPost(){
      return "redirect:/map/search";
   }
//    @GetMapping("/map/search/{id}")
//       public String boardView(Model model, @PathVariable("id") long id) {
//       Optional<Point> data = pointRepository.findById(id);
//       Point point = data.get();
//       model.addAttribute("point", point);
//       return "map/where";
//    }


   private double distance(double lat1, double lon1, double lat2, double lon2) {
      double theta = lon1 - lon2;
      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                  * Math.cos(deg2rad(theta));
      dist = Math.acos(dist);
      dist = rad2deg(dist);
      dist = dist * 60 * 1.1515;
      dist = dist * 1.609344;
      return dist;
   }

   private double deg2rad(double deg) {
      return deg * Math.PI / 180.0;
   }

   private double rad2deg(double rad) {
      return rad * 180 / Math.PI;
   }

   @GetMapping("/map/getPoint")
   @ResponseBody
   public List<Point> getPoint(double lat, double lng, int km) {
      // List<Point> list = pointRepository.findByLatLng(lat, lng, lat, km);

      List<Point> list = pointRepository.findAll();
      for (int i = list.size() - 1; i >= 0; i--) {
         double dist = 
            distance(lat, lng, list.get(i).getLatitude(), list.get(i).getLongitude());
         if (dist > km) {
            list.remove(i);
         }
      }
      return list;
   }
   
   @GetMapping("/map/where")
   public String where(){
      return "/map/where";
   }

}