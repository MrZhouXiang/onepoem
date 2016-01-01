package com.puyun.myshop.daoImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.puyun.myshop.dao.ModelDao;
import com.puyun.myshop.dao.PushDao;
import com.puyun.myshop.entity.BuyerForOrder;
import com.puyun.myshop.entity.GoodsType;
import com.puyun.myshop.entity.Model;
import com.puyun.myshop.entity.MouldForApp;
import com.puyun.myshop.entity.MuBanTupian;
import com.puyun.myshop.entity.OrderForApp;
import com.puyun.myshop.entity.UserType;

/**
 * 推送相关数据访问接口实现类
 */
public class PushDaoImpl implements PushDao {
	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(
				jdbcTemplate.getDataSource());
	}

	@Override
	public List<OrderForApp> getOrderList(int designerId) {
		// TODO Auto-generated method stub
		//查询出所有待发货订单
    	String sql = "select ID as id,saler_username as salerName,MaijiaID as MaijiaID,MaijiaLx as MaijiaLx from dingdan where MmaijiaID=? and MmaijiaLx='shanghu' and Zhuangtai='8'";
    	List<OrderForApp> list = jdbcTemplate.query(sql, new Object[] { designerId },
				new BeanPropertyRowMapper<OrderForApp>(OrderForApp.class));
    	//把所有待发货订单更改为退款状态
    	String updateSql = "UPDATE dingdan SET Tuikuanzht='1' where MmaijiaID=? and MmaijiaLx='shanghu' and Zhuangtai='8'";
		jdbcTemplate.update(updateSql, new Object[]{designerId});
		//更改与封号设计师相关的抢单信息
		String sql2 = "SELECT ShangpinID,Bianhao FROM dingdan WHERE IsQiangdan='1' AND Zhuangtai=1 AND MmaijiaID=? AND MmaijiaLx='shanghu'";
		List<OrderForApp> result = jdbcTemplate.query(sql2, new Object[] { designerId },
				new BeanPropertyRowMapper<OrderForApp>(OrderForApp.class));
		try {
			for(OrderForApp order : result){
				//查询抢单设计师列表
				String sql3 =
			            "SELECT a.ID AS userId,b.ID AS dingdanID, a.UserName as UserName,a.Dengji,b.Youhuijia,b.KuaidiFs,b.jiagongSj,b.Zhuangtai as zhuangTai,b.Tuikuanzht as tuikuanzht "
			                + " FROM loginshanghu AS a JOIN dingdan AS b ON b.MmaijiaID = a.ID WHERE b.ShangpinID = " + order.getShangpinID()
			                + " AND b.Zhuangtai in (1,0)  "
			                + " ORDER BY b.Zhuangtai DESC,a.Dengji DESC,b.jiagongSj ASC,b.KuaidiFs ASC,b.RukuSj ASC ";
			    List<BuyerForOrder> buyerList =
			            jdbcTemplate.query(sql3, new BeanPropertyRowMapper<BuyerForOrder>(BuyerForOrder.class));
			    if(buyerList.size() > 1){
			    	//重新查出一个最NB设计师
			    	String sql4 =
				            "SELECT a.ID,a.UserName,a.VIPDengji,b.JiagongSj,b.ID as dingdanID FROM loginshanghu AS a RIGHT JOIN dingdan AS b ON a.ID = b.MmaijiaID WHERE"
				                + " b.ShangpinID = "
				                + order.getShangpinID()
				                + " AND b.IsQiangdan = 1"
				                + " AND b.Zhuangtai= 0 "
				                + " AND (a.unlock_time < NOW() OR a.unlock_time IS NULL) "
				                + " ORDER BY a.VIPDengji DESC,a.Dengji DESC,b.JiagongSj ASC LIMIT 1";
			    	System.out.println("ShangpinID======== " + order.getShangpinID());
					Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql4);
					if(resultMap != null && resultMap.size() > 0){
						String sql5 = "UPDATE dingdan SET Zhuangtai=1 WHERE ID=?";
						int num1 = jdbcTemplate.update(sql5, new Object[]{resultMap.get("dingdanID")});
						System.out.println("num1============= " + num1);
						String sql6 = "UPDATE dingdan SET Zhuangtai=0 WHERE Bianhao=?";
						int num2 = jdbcTemplate.update(sql6, new Object[]{order.getBianhao()});
						System.out.println("num2============= " + num2);
					}
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		//商品下架
//		String soldOut = "UPDATE shangpin SET IsXiajia='1' WHERE FabuzheID=? and MmaijiaLx='shanghu' ";
//		jdbcTemplate.update(soldOut, new Object[]{designerId});
    	return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTodayBuyoutOrderCount(int goodsId) {
		// TODO Auto-generated method stub
		String sql = " SELECT t1.FabuzheID,COUNT(t1.FabuzheID) AS num,SUM(t1.Zongjia) AS totalFee,l.UserName AS username "
				+ " FROM(SELECT d.ID,d.ShangpinID,d.RukuSj,d.Zongjia,s.FabuzheID "
				+ " FROM "
				+ " dingdan d "
				+ " RIGHT JOIN shangpin s ON d.ShangpinID = s.ID "
				+ " WHERE "
				+ " s.Leixing = 'shejixiu' "
				+ " AND s.FabuzheLx = 'shanghu' "
				+ " AND date(d.FukuanSj) = curdate() "
				+ " AND d.ShangpinID = ? "
				+ " ) t1 "
				+ " LEFT JOIN loginshanghu l ON t1.FabuzheID = l.ID "
				+ " GROUP BY t1.FabuzheID ";
	    List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{goodsId});
	    return list.size() > 0 ? list.get(0) : Collections.EMPTY_MAP;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTodayDesignerTradeAmount(int goodsId) {
		// TODO Auto-generated method stub
		String sql = " SELECT d.MmaijiaID,l.UserName AS username,SUM(Zongjia) AS totalFee "
							+	" FROM "
							+ " dingdan d "
							+ " RIGHT JOIN loginshanghu l ON d.MmaijiaID = l.ID "
							+ " WHERE "
							+ " d.Zhuangtai IN (4, 8, 16, 32, 64, 128, 256) "
							+ " AND d.MmaijiaLx = 'shanghu' "
							+ " AND date(d.FukuanSj) = curdate() "
							+ " AND d.ShangpinID = ? "
							+ " GROUP BY d.MmaijiaID ";
	    List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{goodsId});
	    return list.size() > 0 ? list.get(0) : Collections.EMPTY_MAP;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTodayDiscussCount(int goodsId) {
		// TODO Auto-generated method stub
		String sql = " SELECT s.Mingcheng AS goodsName,p.PinglunDxID,COUNT(p.PinglunDxID) AS num "
							+ " FROM "
							+ " pinglun p "
							+ " RIGHT JOIN shangpin s ON p.PinglunDxID = s.ID "
							+ " WHERE "
							+ " p.Leixing = 'shangpin' "
							+ " AND p.PinglunDxID = ? "
							+ " GROUP BY p.PinglunDxID ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[]{goodsId});
		return list.size() > 0 ? list.get(0) : Collections.EMPTY_MAP;
	}
	
	@Override
	public String getTelByUserId(String userId, String userType) {
		// TODO Auto-generated method stub
		String sql = "";
		String tel = "";
		if(UserType.BUYER.getValue().equals(userType)){
			sql = "SELECT TEL FROM login WHERE ID=(SELECT LoginID FROM loginkehu WHERE ID=?)";
			tel = jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
		}else{
			sql = "SELECT TEL FROM login WHERE ID=(SELECT LoginID FROM loginshanghu WHERE ID=?)";
			tel = jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
		}
		return tel;
	}
	@Override
	public String getUsername(String userId, String userType) {
		// TODO Auto-generated method stub
		String sql = "";
		String username = "";
		if(UserType.BUYER.getValue().equals(userType)){
			sql = "SELECT UserName FROM loginkehu WHERE ID=?";
			username = jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
		}else{
			sql = "SELECT UserName FROM loginshanghu WHERE ID=?";
			username = jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
		}
		return username;
	}
}
