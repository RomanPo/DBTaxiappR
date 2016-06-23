package ua.artcode.taxi.dao;

import ua.artcode.taxi.model.Order;
import ua.artcode.taxi.model.OrderStatus;
import ua.artcode.taxi.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class AppDB {

    private static int orderIdCounter = 1;
    private Map<User, List<Order>> users;
    private Collection<Order> orders;

    public AppDB() {
        users = new HashMap<User, List<Order>>();
        orders = new ArrayList<Order>();
    }

    public AppDB(Map<User, List<Order>> users, List<Order> orders) {
        this.users = users;
        this.orders = orders;
    }

    public Map<User, List<Order>> getUsers() {
        return users;
    }

    public void setUsers(Map<User, List<Order>> users) {
        this.users = users;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "AppDB{" +
                "users=" + users.toString() +
                ", orders=" + orders.toString() +
                '}';
    }

    public User addUser(User user) {

        try {
            Statement statement = DBConnectionFactory.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO users(id, identifier, phone, password, name, homeAddress) VALUES (" + user.getId() + ", " + user.getIdentifier() + ", " + user.getPhone() + ", " +
                    "" + user.getPass() + ", " + user.getName() + ", " + user.getHomeAddress() + "");

            DBConnectionFactory.getConnection().commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                DBConnectionFactory.getConnection().close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }

        return user;
    }

    public Order addOrder(Order order) {

        try {
            PreparedStatement preparedStatement = DBConnectionFactory.getConnection().prepareStatement("INSER INTO oders(OrderID, OrderStatus, AddressFrom, " +
                    "AddressTo, Passenger, Driver, Distance, Price, Message) VALUES (?,?,?,?,?,?,?,?,?,?)");
            {
                preparedStatement.setLong(1, order.getId());
                preparedStatement.setString(2, "NEW");
                preparedStatement.setString(3, order.getFrom().toString());
                preparedStatement.setString(4, order.getTo().toString());
                preparedStatement.setString(5, order.getPassenger().toString());
                preparedStatement.setString(6, order.getDriver().toString());
                preparedStatement.setInt(7, order.getDistance());
                preparedStatement.setInt(8, order.getPrice());
                preparedStatement.setString(9, order.getMessage());

                preparedStatement.execute();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    public Order findOrder(long id) {
        try {
            Statement statement = DBConnectionFactory.getConnection().
                    createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM oders WHERE OrederId = "+id+"");
            while(resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getInt("OrederId"));
                order.setOrderStatus(OrderStatus.NEW);

            }


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
  return null;
    }

    public User findUser(String phone) {
        for (User user : users.keySet()) {
            if (user.getPhone().equals(phone)) {
                return user;
            }
        }
        return null;
    }

    public Order addOrderToDriver(User user, Order order) {

        List<Order> newList = users.get(user);
        newList.add(order);
        users.replace(user, newList);

        return order;
    }
}