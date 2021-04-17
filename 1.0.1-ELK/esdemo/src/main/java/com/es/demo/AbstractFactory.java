package com.es.demo;

public class AbstractFactory {

    public static void main(String[] args) {

        IDataBaseUtils iDataBaseUtils = new OracleDataBaseUtils();
        Iconnection connection = iDataBaseUtils.getConnection();
        connection.connection();
        ICommand comand = iDataBaseUtils.comand();
        comand.command();

    }

}

//  数据库组件 mysql,    oracle, sqlserver,  ....
//          connection , command   .    ....
interface Iconnection{

    void connection();

}

interface ICommand{

    void command();

}

interface IDataBaseUtils{

    Iconnection getConnection();
    ICommand comand();

}


class MysqlConnection implements Iconnection {

    public void connection() {
        System.out.println("mysql connection");
    }
}

class MysqlCommand implements ICommand{

    public void command() {
        System.out.println("mysql command");
    }
}

class MysqlDataBaseUtils implements IDataBaseUtils{

    public Iconnection getConnection() {
        return new MysqlConnection();
    }

    public ICommand comand() {
        return new MysqlCommand();
    }
}

class OracleConnection implements Iconnection{

    public void connection() {
        System.out.println("oracle connection");
    }
}

class OracleCommand implements ICommand{

    public void command() {
        System.out.println("oracle command");
    }
}

class OracleDataBaseUtils implements IDataBaseUtils{
    public Iconnection getConnection() {
        return new OracleConnection();
    }

    public ICommand comand() {
        return new OracleCommand();
    }
}