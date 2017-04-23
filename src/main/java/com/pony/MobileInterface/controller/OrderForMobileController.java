package com.pony.MobileInterface.controller;

import com.google.gson.Gson;
import com.pony.MobileInterface.entity.ChildOrder;
import com.pony.MobileInterface.entity.ProductOrder;
import com.pony.MobileInterface.entity.ProductTemp;
import com.pony.MobileInterface.entity.UsableContainerTypeAndNumber;
import com.pony.MobileInterface.entity.queryBean.ChildOrderQueryBean;
import com.pony.MobileInterface.entity.queryBean.ProductOrderQueryBean;
import com.pony.MobileInterface.service.ProductForMobileService;
import com.pony.MobileInterface.service.ProductOrderForMobileService;
import com.pony.MobileInterface.service.SelfLiftingCabinetForMobileService;
import com.pony.MobileInterface.service.TimeCodeForMobileService;
import com.pony.MobileInterface.util.ContainerCalculateUtil;
import com.pony.MobileInterface.util.ProductOrderNumberGenerator;
import com.pony.MobileInterface.util.ProductUtil;
import com.pony.productManage.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Created by zhangmingyue on 2017/3/2 0002.
 * 订单相关接口
 */
@RestController
@RequestMapping("/order")
public class OrderForMobileController {

    private static final Logger log = LoggerFactory.getLogger(OrderForMobileController.class);
    private Gson gson = new Gson();


    @Autowired
    private ProductForMobileService productForMobileService;
    @Autowired
    private TimeCodeForMobileService timeCodeForMobileService;
    @Autowired
    private SelfLiftingCabinetForMobileService selfLiftingCabinetForMobileService;
    @Autowired
    private ProductOrderForMobileService productOrderForMobileService;
    /**
     * 根据查询条件获取订单
     */
    @RequestMapping(value = "/getProductOrderListByQueryBean", method = RequestMethod.GET)
    public String getProductOrderListByQueryBean(ProductOrderQueryBean productOrderQueryBean){
        List<ProductOrder> productOrderList = productOrderForMobileService.getProductOrderListByQueryBean(productOrderQueryBean);
        return gson.toJson(productOrderList);
    }
    /**
     * 根据订单ID获取订单详细信息
     */
    @RequestMapping(value = "/getProductOrderByOrderId", method = RequestMethod.GET)
    public String getProductOrderByOrderId(Integer productOrderId) {
        ProductOrder productOrder = productOrderForMobileService.getProductOrderByOrderId(productOrderId);

        return  gson.toJson(productOrder);
//        return ""+productOrderId;
    }
    /**
     * 根据查询条件获取各种状态子订单
     */
    @RequestMapping(value = "/getChildOrderByQueryBean", method = RequestMethod.GET)
    public String getChildOrderByQueryBean(ChildOrderQueryBean childOrderQueryBean) {
        List<ChildOrder> childOrderList = productOrderForMobileService.getChildOrderByQueryBean(childOrderQueryBean);
        childOrderQueryBean.getBeginLine();
        return gson.toJson(childOrderList);
//        return "";
    }
    /**
     * 查看时间代码是否可用
     */
    @RequestMapping(value = "/checkTimeCodeIsUsable", method = RequestMethod.GET)
    public String checkTimeCodeIsUsable(String[] shoppingCartIds,String deliveryDate,int timeCode,int selfLiftingCabinetId) {
        int check = 0;//0为可用，1为不可用
        //获取产品尺寸与数量信息
        List<ProductTemp> productTempList = timeCodeForMobileService.getProductTempList(shoppingCartIds);
        //获取可用自提柜信息
        List<UsableContainerTypeAndNumber> usableContainerTypeAndNumberList = selfLiftingCabinetForMobileService.getUsableContainerTypeAndNumber(selfLiftingCabinetId,deliveryDate,timeCode);
        //检查时间点是否可用
        check = ContainerCalculateUtil.checkTimeCodeIsUsable(productTempList, usableContainerTypeAndNumberList);
        return gson.toJson(check);
//        System.out.println(deliveryDate);
//        System.out.println(shoppingCartIds);
//        System.out.println(timeCode);
//        System.out.println(selfLiftingCabinetId);
//        return "";
    }
    /**
     * 生成订单
     */
    @RequestMapping(value = "/creatOrderAndChildOrders", method = RequestMethod.GET)
    public String creatOrderAndChildOrders(int userId,int addressId,String deliveryDate,int timeCode,int selfLiftingCabinetId
                                           ,String[] shoppingCartIds) {

        int check = 1;//0为可用，1为不可用
        //获取产品尺寸与数量信息
        List<ProductTemp> productTempList = timeCodeForMobileService.getProductTempList(shoppingCartIds);
        //获取可用自提柜信息
        List<UsableContainerTypeAndNumber> usableContainerTypeAndNumberList = selfLiftingCabinetForMobileService.getUsableContainerTypeAndNumberAndList(selfLiftingCabinetId,deliveryDate,timeCode);
        //检查时间点是否可用
        List<ChildOrder> childOrderList = ContainerCalculateUtil.loader(productTempList,usableContainerTypeAndNumberList);
        if(childOrderList==null){
            return gson.toJson(check);
        }else{
            check =0;
        }
        double cost = 0;
        for(ProductTemp pt:productTempList){
            cost+=pt.getNumber()* ProductUtil.getProductNowPrice(pt.getProduct());
        }
        ProductOrder productOrder = new ProductOrder();
        productOrder.setChildOrderList(childOrderList);
        productOrder.setAddressId(addressId);
        productOrder.setUserId(userId);
        productOrder.setCost(cost);
        productOrder.setProductOrderNumber(ProductOrderNumberGenerator.getProductOrderNumber());
//        productOrder.setChildOrderList(childOrderList);
        //保存订单并获取订单ID
        check = productOrderForMobileService.addProductOrder(productOrder);
        //todo 删除购物车

        return gson.toJson(check);
//        System.out.println(deliveryDate);
//        System.out.println(shoppingCartIds);
//        System.out.println(timeCode);
//        System.out.println(userId);
//        System.out.println(addressId);
//        System.out.println(selfLiftingCabinetId);
//        return "";
    }


}