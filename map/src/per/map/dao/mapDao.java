package per.map.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import per.datasources.createDataSources;
import per.datasources.dataSourcesConfig;
import per.map.serviceimpl.buildingObject;
import per.map.serviceimpl.coordinateObject;
import per.map.serviceimpl.typeNameObject;

public class mapDao {
	private Connection connect = null;
	private PreparedStatement st = null;
	private final static Object deleteTypeLock = new Object();
	private createDataSources dateSources = null;
	private ResultSet rs = null;
	
	public mapDao() {
		dateSources = new createDataSources();
		connect = dateSources.getConnection();
	}
	
	/**
	 * @return ArrayList<buildingObject> 信息的列表
	 * 
	 * 查询所有建筑的信息
	 * 
	 * @since 2018.5.8
	 * @author Administrator
	 * */
	public ArrayList<buildingObject> getBuildingCoordinate(){
		ArrayList<buildingObject> list = new ArrayList<buildingObject>();
		String sql = "";
		sql += "SELECT A.BUILDINGID, A.LONGITUDE,A.LATITUDE,";
		sql += "B.BUILDINGNAME,B.BUILDINGTYPEID,B.TELEPHONE,B.ADDRESS,B.PIC,C.TYPENAME ";
		sql += "FROM ((BUILDINGSITE A ";
		sql += "LEFT JOIN BUILDING B ON B.ID = A.BUILDINGID) ";
		sql += "LEFT JOIN BUILDINGTYPE C ON B.BUILDINGTYPEID = C.ID) ";
		sql += "WHERE A.LONGITUDE > 0 AND A.LATITUDE > 0";
		try {
			st = connect.prepareStatement(sql);
			rs = st.executeQuery();
			int preBuildingID = 0;
			buildingObject preObject = null;
			while(rs.next()) {
				int ID = rs.getInt(1);
				if(preBuildingID == ID){
					coordinateObject coor = new coordinateObject();
					coor.setLATITUDE(rs.getFloat("LATITUDE"));
					coor.setLONGITUDE(rs.getFloat("LONGITUDE"));
					preObject.getList().add(coor);
				}else{
					buildingObject building = new buildingObject();
					preObject = building;
					
					building.setID(ID);
					
					coordinateObject coor = new coordinateObject();
					coor.setLATITUDE(rs.getFloat("LATITUDE"));
					coor.setLONGITUDE(rs.getFloat("LONGITUDE"));
					building.getList().add(coor);
					
					building.setBUILDINGNAME(rs.getString("BUILDINGNAME"));
					building.setBUILDINGTYPE(rs.getString("BUILDINGTYPEID"));
					String tel = rs.getString("TELEPHONE");
					if(tel == "" || tel == null){
						tel = "暂无";
					}
					building.setTELEPHONE(tel);
					building.setADDRESS(rs.getString("ADDRESS"));
					String picName = rs.getString("PIC");
					if(picName == "" || picName == null){
						building.setPIC(
								dataSourcesConfig.picURI+"/default.jpg"
							);
					}else{
						building.setPIC(
								dataSourcesConfig.picURI+"/"+
								building.getBUILDINGTYPE()+"/"+
								picName
							);
					}
					
					building.setTYPENAME(rs.getString("TYPENAME"));
					list.add(building);
				}
				preBuildingID = ID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connect != null){
				try {
					connect.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * @param String 建筑的类别名称
	 * @return int 更新的函数
	 * 
	 * 添加建筑的类别
	 * 
	 * @since 2018.5.8
	 * @author Administrator
	 * */
	public int addBuildingType(String typename) {
		int updateRow = 0;
		String sql = "INSERT INTO BUILDINGTYPE (TYPENAME) VALUES (?)";
		try {
			st = connect.prepareStatement(sql);
			st.setString(0, typename);
			updateRow = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dateSources.closeConnection();
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return updateRow;
	}
	
	/**
	 * 获取所有的建筑类别
	 * 
	 * @since2018.5.8
	 * @author Administrator
	 * */
	public ArrayList<typeNameObject> getAllTypeName(){
		ArrayList<typeNameObject> list = new ArrayList<>();
		String sql = "SELECT * FROM BUILDINGTYPE";
		try {
			st = connect.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				typeNameObject nameObject = new typeNameObject();
				nameObject.setID(rs.getInt(1));
				nameObject.setTYPENAME(rs.getString(2));
				list.add(nameObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					rs.close();
					st.close();
					connect.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * @param int 类别的id
	 * 
	 * 判断这个类别是否被使用
	 * 如果没有被用过可以直接删掉
	 * 注：加锁防止多次执行删除（不加也可以）
	 * 
	 * @since2018.5.8
	 * @author Administrator
	 * */
	public int deletTypeName(int typeid) {
		int buildingRow = 0;
		String rowSQL = "SELECT COUNT(*) FROM BUILDING WHERE BUILDINGTYPE = ?";
		synchronized (deleteTypeLock) {
			try {
				st = connect.prepareStatement(rowSQL);
				st.setInt(1, typeid);
				ResultSet rs = st.executeQuery();
				buildingRow = rs.getInt(1);
				if(buildingRow > 0) {
					String delSQL = "DELETE FROM BUILDINGTYPE WHERE ID = ?";
					st = connect.prepareStatement(delSQL);
					st.setInt(1, typeid);
					st.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(st != null){
					try {
						st.close();
						connect.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return buildingRow;
	}
}
