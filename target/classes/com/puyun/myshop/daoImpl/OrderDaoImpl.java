package com.puyun.myshop.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.puyun.myshop.dao.OrderDao;
import com.puyun.myshop.entity.Buyer;
import com.puyun.myshop.entity.Order;
import com.puyun.myshop.entity.OrderBean;
import com.puyun.myshop.entity.Saler;
import com.puyun.myshop.entity.UserType;

/**
 * 订单管理相关数据访问接口实现类
 * 
 * @author ldz 创建时间: 2015-2-10
 */
public class OrderDaoImpl implements OrderDao
{
    private JdbcTemplate jdbcTemplate;
    
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate()
    {
        return this.jdbcTemplate;
    }
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }
    
    @Override
    public List<OrderBean> getList(String keyword, int start, int num, int status, int loanStatus)
    {
        String statusStr = "";
        if (status != -1)
        {
            if(status == 0){
            	statusStr = " and Zhuangtai in(0,1)"  + " ";
            }else{
            	statusStr = " and Zhuangtai=" + status + " ";
            }
        }
        String loanStatusStr = "";
        if(loanStatus != -1){
        	loanStatusStr = " and FangkuanZht=" + loanStatus + "";
        }
        String key = "%" + keyword + "%";
        String sql ="select ID as id,ShangpinID as goods_id,Bianhao as order_no,Zhuangtai as status,Shuliang as quantity,Zongjia as totalPrice,"
        		+ "MaijiaID as buyer_id,buyer_username as buyerName,MmaijiaID as saler_id,saler_username as salerName,FangkuanZht from dingdan where (Bianhao like ? OR buyer_username like ? OR saler_username like ?)"
        		+ statusStr
        		+ loanStatusStr
        		+ " order by Zhuangtai desc,RukuSj desc" + " limit " + start + "," + num;
        List<OrderBean> list = jdbcTemplate.query(sql, new Object[] {key, key, key}, new BeanPropertyRowMapper<OrderBean>(OrderBean.class));
        for(OrderBean order : list){
        	String sql1 = "";
        	if(UserType.BUYER.getValue().equals(order.getMaijialx())){
        		sql1 = "select UserName as name from LoginKehu where ID=?";
        		List<Buyer> buyerList = jdbcTemplate.query(sql1, new Object[] {order.getBuyer_id()}, new BeanPropertyRowMapper<Buyer>(Buyer.class));
                if(buyerList.size() > 0){
                	Buyer buyer = buyerList.get(0);
                    order.setBuyerName(buyer.getName());
                }
        	}else{
        		sql1 = "select UserName as name from LoginShanghu where ID=?";
        		List<Saler> salerList = jdbcTemplate.query(sql1, new Object[] {order.getBuyer_id()}, new BeanPropertyRowMapper<Saler>(Saler.class));
                if(salerList.size() > 0){
                	Saler saler = salerList.get(0);
                    order.setBuyerName(saler.getName());
                }
        	}
            String sql2 = "select UserName as name,ZhifuZhanghu as zhifuzhanghu from LoginShanghu where ID=?";
            List<Saler> salerList = jdbcTemplate.query(sql2, new Object[] {order.getSaler_id()}, new BeanPropertyRowMapper<Saler>(Saler.class));
            if(salerList.size() > 0){
                Saler saler = salerList.get(0);
                order.setSalerName(saler.getName());
                order.setSalerPayAccount(saler.getZhifuzhanghu());
            }
        }
        return list;
    }
    
    
    @Override
    public int getListCount(String keyword, int status)
    {
        String key = "%" + keyword + "%";
        String statusStr = "";
        if (status != -1)
        {
        	if(status == 0){
            	statusStr = " and Zhuangtai in(0,1)"  + " ";
            }else{
            	statusStr = " and Zhuangtai=" + status + " ";
            }
        }
        String sql ="select ID as id,ShangpinID as goods_id,Bianhao as order_no,Zhuangtai as status,Shuliang as quantity,Zongjia as totalPrice,"
        		+ "MaijiaID as buyer_id, MmaijiaID as saler_id from dingdan where (Bianhao like ? OR buyer_username like ? OR saler_username like ?)"
        		+ statusStr
                + " order by RukuSj desc";
        @SuppressWarnings("deprecation")
        int num = jdbcTemplate.queryForInt("select count(*) from(" + sql + ") t", key, key, key);
        return num;
    }
    
    @Override
    public Map<String, Object> getOrderCount() {
    	// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
    	int allCount = jdbcTemplate.queryForInt("select count(*) from dingdan");
    	int loaningCount = jdbcTemplate.queryForInt("select count(*) from dingdan where FangkuanZht=0");
    	map.put("allCount", allCount);
    	map.put("loaningCount", loaningCount);
    	return map;
    }
    
    @Override
    public OrderBean getOrderDetail(int id) {
    	// TODO Auto-generated method stub
    	String sql = " select ID as id,Bianhao as order_no,Zhuangtai as status,Shuliang as quantity, "
    			+ " Zongjia as totalPrice,MaijiaID as buyer_id, MmaijiaID as saler_id,FangkuanZht as fangkuanZht, "
    			+ " Chima as sizename,Shengao as height,Tizhong as weight,Yaowei as waist,Xiongwei as bust, "
    			+ " Tunwei as hip,Miaoshu as description,Shouhuoren as shouhuoren,ShouhuoDianhua as shouhuodianhua, "
    			+ " ShouhuoDizhi as shouhuodizhi,Youbian as Youbian,Shoukuanma as paymentCode,RukuSj as RukuSj, "
    			+ " IsQiangdan as qiangdan,payCode as alipayCode,FahuoSj,DaoqiSj,ShangpinID as goods_id from dingdan "
    			+ " where ID=? ";
    	 List<OrderBean> list = jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<OrderBean>(OrderBean.class));
         for(OrderBean order : list){
         	String sql1 = "";
         	if(UserType.BUYER.getValue().equals(order.getMaijialx())){
         		sql1 = "select UserName as name from LoginKehu where ID=?";
         		List<Buyer> buyerList = jdbcTemplate.query(sql1, new Object[] {order.getBuyer_id()}, new BeanPropertyRowMapper<Buyer>(Buyer.class));
                 if(buyerList.size() > 0){
                 	Buyer buyer = buyerList.get(0);
                     order.setBuyerName(buyer.getName());
                 }
         	}else{
         		sql1 = "select UserName as name from LoginShanghu where ID=?";
         		List<Saler> salerList = jdbcTemplate.query(sql1, new Object[] {order.getBuyer_id()}, new BeanPropertyRowMapper<Saler>(Saler.class));
                 if(salerList.size() > 0){
                 	Saler saler = salerList.get(0);
                     order.setBuyerName(saler.getName());
                 }
         	}
             String sql2 = "select UserName as name,ZhifuZhanghu as zhifuzhanghu from LoginShanghu where ID=?";
             List<Saler> salerList = jdbcTemplate.query(sql2, new Object[] {order.getSaler_id()}, new BeanPropertyRowMapper<Saler>(Saler.class));
             if(salerList.size() > 0){
                 Saler saler = salerList.get(0);
                 order.setSalerName(saler.getName());
                 order.setSalerPayAccount(saler.getZhifuzhanghu());
             }
         }
         return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    public boolean updateOrderStatus(int id, int status) {
    	// TODO Auto-generated method stub
    	String sql = "update dingdan set Zhuangtai=? where ID=?";
    	int num = jdbcTemplate.update(sql, new Object[]{status, id});
    	return num > 0;
    }
    
    @Override
    public boolean updateLoanStatus(int id, int status) {
    	// TODO Auto-generated method stub
    	String sql = "update dingdan set FangkuanZht=? where ID=?";
    	int num = jdbcTemplate.update(sql, new Object[]{status, id});
    	return num > 0;
    }
}
