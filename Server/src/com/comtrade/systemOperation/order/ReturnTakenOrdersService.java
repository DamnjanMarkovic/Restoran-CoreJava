package com.comtrade.systemOperation.order;

import com.code.domain.Offer;
import com.code.domain.Order;
import com.code.domain.OrderDTO;
import com.code.transferClass.TransferClass;
import com.comtrade.broker.Broker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnTakenOrdersService extends com.comtrade.systemOperation.GenericSystemOperation {
    @Override
    public void executeSpecificOperation(TransferClass transferClass) throws SQLException, InterruptedException, IOException {

        List<Offer> listRequestedOffers = (List<Offer>) transferClass.getRequest();
        Broker broker = new Broker();
        List<OrderDTO> listOrderDTO = broker.getFinalOrderList(prepareLlistIDOrders(transferClass.getMessage()));

        transferClass.setResponse(createFinalOrderList(listOrderDTO, listRequestedOffers));


    }

    private List<Order> createFinalOrderList(List<OrderDTO> listOrderDTO, List<Offer>listRequestedOffers) {
        List<Order> finalOrderList = new ArrayList<>();
        Order order;

        for (OrderDTO orderDTO:listOrderDTO) {
            for (Offer offers: listRequestedOffers                 ) {
                    if (orderDTO.getId_offer()==offers.getId_restaurant_menu()){
                        order = new Order(orderDTO.getId_order(), offers, orderDTO.getQuantity());
                        finalOrderList.add(order);
                    }
            }
        }
    return finalOrderList;
    }

    private List<Integer> prepareLlistIDOrders(String message) {
        String[] tokens = message.split(";");
        int number = Integer.parseInt(tokens[0])+1;
        List<Integer>listIDOrders = new ArrayList<>();
        for (int i = 1; i <number; i++) {
            listIDOrders.add(Integer.parseInt(tokens[i]));
        }return listIDOrders;
    }
}
