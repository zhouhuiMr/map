package per.map.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import per.common.tool.jsonResult;
import per.map.dao.mapDao;

public class mapServiceImpl {
	private mapDao dao = null;
	
	public mapServiceImpl() {
		dao = new mapDao();
	}
	
	public String getMapCoordinate() {
		String json = "";
		jsonResult result = new jsonResult();
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<buildingObject> list = dao.getBuildingCoordinate();
		if(list != null) {
			HashMap<String, ArrayList<buildingObject>> map = new HashMap<>();
			for(int i=0;i<list.size();i++){
				//将坐标对象进行排序
				String typename = list.get(i).getTYPENAME();
				if(map.containsKey(typename)){
					map.get(typename).add(list.get(i));
				}else{
					ArrayList<buildingObject> l = new ArrayList<>();
					l.add(list.get(i));
					map.put(typename, l);
				}
			}
			result.setCode("0000");
			result.setData(map);
			result.setMessage("success");
		}else {
			result.setCode("0001");
			result.setData("");
			result.setMessage("failed");
		}
		try {
			json = mapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
