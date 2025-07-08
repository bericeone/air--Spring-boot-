package com.air.controller;

import com.air.entity.Flight;
import com.air.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/flight")
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String flightNumber,
                       @RequestParam(required = false) String departureCity,
                       @RequestParam(required = false) String arrivalCity,
                       @RequestParam(required = false) Integer status,
                       Model model) {
        model.addAttribute("flights", flightService.findByPage(page, size, flightNumber, departureCity, arrivalCity, status));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", flightService.getTotalPages(size, flightNumber, departureCity, arrivalCity, status));
        return "flight/list";
    }

    @GetMapping("/add")
    public String addPage() {
        return "flight/add";
    }

    @PostMapping("/add")
    public String add(Flight flight, @RequestParam(required = false) MultipartFile image, Model model) {
        try {
            logger.info("开始添加航班，航班信息：{}", flight);

            // 验证必填字段
            if (flight.getFlightNumber() == null || flight.getFlightNumber().trim().isEmpty()) {
                logger.warn("航班号不能为空");
                model.addAttribute("error", "航班号不能为空");
                return "flight/add";
            }
            if (flight.getDepartureCity() == null || flight.getDepartureCity().trim().isEmpty()) {
                logger.warn("出发城市不能为空");
                model.addAttribute("error", "出发城市不能为空");
                return "flight/add";
            }
            if (flight.getArrivalCity() == null || flight.getArrivalCity().trim().isEmpty()) {
                logger.warn("到达城市不能为空");
                model.addAttribute("error", "到达城市不能为空");
                return "flight/add";
            }
            if (flight.getDepartureTime() == null) {
                logger.warn("出发时间不能为空");
                model.addAttribute("error", "出发时间不能为空");
                return "flight/add";
            }
            if (flight.getArrivalTime() == null) {
                logger.warn("到达时间不能为空");
                model.addAttribute("error", "到达时间不能为空");
                return "flight/add";
            }
            if (flight.getPrice() == null || flight.getPrice().compareTo(java.math.BigDecimal.ZERO) < 0) {
                logger.warn("价格必须大于等于0");
                model.addAttribute("error", "价格必须大于等于0");
                return "flight/add";
            }
            if (flight.getTotalSeats() == null || flight.getTotalSeats() < 1) {
                logger.warn("总座位数必须大于0");
                model.addAttribute("error", "总座位数必须大于0");
                return "flight/add";
            }
            if (flight.getAvailableSeats() == null || flight.getAvailableSeats() < 0) {
                logger.warn("可用座位数必须大于等于0");
                model.addAttribute("error", "可用座位数必须大于等于0");
                return "flight/add";
            }
            if (flight.getStatus() == null) {
                logger.warn("状态不能为空");
                model.addAttribute("error", "状态不能为空");
                return "flight/add";
            }

            // 验证业务规则
            if (flight.getAvailableSeats() > flight.getTotalSeats()) {
                logger.warn("可用座位数不能大于总座位数");
                model.addAttribute("error", "可用座位数不能大于总座位数");
                return "flight/add";
            }

            if (flight.getArrivalTime().isBefore(flight.getDepartureTime())) {
                logger.warn("到达时间必须晚于出发时间");
                model.addAttribute("error", "到达时间必须晚于出发时间");
                return "flight/add";
            }

            // 处理图片上传
            if (image != null && !image.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
                // 使用绝对路径
                String uploadDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "uploads";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    boolean created = dir.mkdirs();
                    if (!created) {
                        logger.error("创建上传目录失败：{}", uploadDir);
                        model.addAttribute("error", "创建上传目录失败");
                        return "flight/add";
                    }
                }
                try {
                    File destFile = new File(dir, fileName);
                    image.transferTo(destFile);
                    flight.setImageUrl("/uploads/" + fileName);
                    logger.info("图片上传成功：{}", destFile.getAbsolutePath());
                } catch (IOException e) {
                    logger.error("图片上传失败", e);
                    model.addAttribute("error", "图片上传失败：" + e.getMessage());
                    return "flight/add";
                }
            }

            // 保存航班信息
            try {
                flightService.add(flight);
                logger.info("航班添加成功：{}", flight);
                return "redirect:/flight/list";
            } catch (Exception e) {
                logger.error("保存航班信息失败", e);
                model.addAttribute("error", "保存航班信息失败：" + e.getMessage());
                return "flight/add";
            }

        } catch (Exception e) {
            logger.error("添加航班失败", e);
            model.addAttribute("error", "添加航班失败：" + e.getMessage());
            return "flight/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("flight", flightService.findById(id));
        return "flight/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(Flight flight, @RequestParam(required = false) MultipartFile image) {
        Map<String, Object> result = new HashMap<>();
        try {
            logger.info("开始编辑航班，航班信息：{}", flight);

            // 验证必填字段
            if (flight.getFlightNumber() == null || flight.getFlightNumber().trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "航班号不能为空");
                return result;
            }
            if (flight.getDepartureCity() == null || flight.getDepartureCity().trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "出发城市不能为空");
                return result;
            }
            if (flight.getArrivalCity() == null || flight.getArrivalCity().trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "到达城市不能为空");
                return result;
            }
            if (flight.getDepartureTime() == null) {
                result.put("success", false);
                result.put("error", "出发时间不能为空");
                return result;
            }
            if (flight.getArrivalTime() == null) {
                result.put("success", false);
                result.put("error", "到达时间不能为空");
                return result;
            }
            if (flight.getPrice() == null || flight.getPrice().compareTo(java.math.BigDecimal.ZERO) < 0) {
                result.put("success", false);
                result.put("error", "价格必须大于等于0");
                return result;
            }
            if (flight.getTotalSeats() == null || flight.getTotalSeats() < 1) {
                result.put("success", false);
                result.put("error", "总座位数必须大于0");
                return result;
            }
            if (flight.getAvailableSeats() == null || flight.getAvailableSeats() < 0) {
                result.put("success", false);
                result.put("error", "可用座位数必须大于等于0");
                return result;
            }
            if (flight.getStatus() == null) {
                result.put("success", false);
                result.put("error", "状态不能为空");
                return result;
            }

            // 验证业务规则
            if (flight.getAvailableSeats() > flight.getTotalSeats()) {
                result.put("success", false);
                result.put("error", "可用座位数不能大于总座位数");
                return result;
            }

            if (flight.getArrivalTime().isBefore(flight.getDepartureTime())) {
                result.put("success", false);
                result.put("error", "到达时间必须晚于出发时间");
                return result;
            }

            // 处理图片上传
            if (image != null && !image.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
                String uploadDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "uploads";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    boolean created = dir.mkdirs();
                    if (!created) {
                        logger.error("创建上传目录失败：{}", uploadDir);
                        result.put("success", false);
                        result.put("error", "创建上传目录失败");
                        return result;
                    }
                }
                try {
                    File destFile = new File(dir, fileName);
                    image.transferTo(destFile);
                    flight.setImageUrl("/uploads/" + fileName);
                    logger.info("图片上传成功：{}", destFile.getAbsolutePath());
                } catch (IOException e) {
                    logger.error("图片上传失败", e);
                    result.put("success", false);
                    result.put("error", "图片上传失败：" + e.getMessage());
                    return result;
                }
            }

            // 更新航班信息
            flightService.update(flight);
            logger.info("航班更新成功：{}", flight);

            result.put("success", true);
            result.put("redirectUrl", "/flight/list");
            return result;

        } catch (Exception e) {
            logger.error("更新航班失败", e);
            result.put("success", false);
            result.put("error", "更新航班失败：" + e.getMessage());
            return result;
        }
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id) {
        try {
            logger.info("开始删除航班，ID：{}", id);
            flightService.delete(id);
            logger.info("航班删除成功，ID：{}", id);
            return "success";
        } catch (Exception e) {
            logger.error("删除航班失败", e);
            return "删除失败：" + e.getMessage();
        }
    }
}