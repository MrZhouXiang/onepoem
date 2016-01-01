package com.puyun.myshop.daoImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.puyun.myshop.ctrl.AppCtrl;
import com.puyun.myshop.dao.BuyerStatisticsDao;

/**
 * 
 * TODO	统计查询相关数据访问接口实现类. 
 * Created on 2015年8月4日 下午3:01:09 
 * @author 周震
 */
public class BuyerStatisticsDaoImpl implements BuyerStatisticsDao{

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
	public int getRegisterUserCount(String keyword1, String keyword2, String keyword3, String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		boolean flag1 = keyword1.length() > 0 && keyword2.length() > 0;
		boolean flag2 = keyword3.length() > 0;
		boolean flag3 = keyword4.length() > 0;
		String sql = " SELECT COUNT(*) FROM loginkehu ";
		if(flag1){
			sql += " WHERE(create_time> " +  "'" + keyword1 + "'" + " AND create_time< " + "'" + keyword2 + "'" + " ) ";
		}
		if(flag2){
			sql += " AND (identity= " + "'" + keyword3 + "'"
					 	+ " OR belong= " + "'" + keyword3 + "'" 
						+ " OR country= " + "'" + keyword3 + "'" 
						+ " OR province= "+ "'" + keyword3 + "'"
						+ " OR city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(flag3){
			sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
					+ " OR company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}

	@Override
	public int getVIPUserCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM loginkehu WHERE VIPDengji > 0 ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND create_time> " +  "'" + keyword1 + "'" + " AND create_time< " + "'" + keyword2 + "'";
		}
		if(keyword3.length() > 0){
			sql += " AND (identity= " + "'" + keyword3 + "'"
					+ " OR belong= " + "'" + keyword3 + "'" 
					+ " OR country= " + "'" + keyword3 + "'" 
					+ " OR province= "+ "'" + keyword3 + "'"
					+ " OR city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
					+ " or company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public int getTotalPraiseCount() {
		// TODO Auto-generated method stub
		Integer num;
		String sql = " SELECT SUM(ZanCt) FROM shangpin ";
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num == null ? 0 : num;
	}
	
	@Override
	public int getTotalDiscussCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT Count(*) FROM pinglun p "
				+ " RIGHT JOIN loginkehu l ON p.PinglunRenID = l.ID "
				+ " WHERE p.PinglunrenLx = 'shanghu' ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (p.RukuSj> " + "'" + keyword1 + "'" + "AND p.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql +=  "AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
					+ " OR company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public int getCancelOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM dingdan d "
				+ " RIGHT JOIN loginkehu l ON d.MaijiaID = l.ID "
				+ " WHERE d.Zhuangtai='2' "
				+ " AND d.MaijiaLx = 'shanghu' ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (d.RukuSj> " + "'" + keyword1 + "'" + "AND d.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
					+ " OR company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public int getSuccessOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM dingdan d "
				+ " RIGHT JOIN loginkehu l ON d.MaijiaID = l.ID "
				+ " WHERE d.Zhuangtai in(4,8,16,32,64,128,256) "
				+ " AND d.MaijiaLx = 'shanghu' ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (d.RukuSj> " + "'" + keyword1 + "'" + "AND d.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
					+ " OR company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public int getWorksheetOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM( "
								+" SELECT "
								+" d.ID,d.ShangpinID,d.RukuSj,s.FabuzheID "
								+" FROM dingdan d "
								+" RIGHT JOIN shangpin s ON d.ShangpinID = s.ID "
								+" WHERE s.Leixing = 'jiagongdan' "
								+" AND s.FabuzheLx = 'shanghu' "
								+" ) t1 "
								+" LEFT JOIN loginkehu l ON t1.FabuzheID = l.ID ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (t1.RukuSj> " + "'" + keyword1 + "'" + "AND t1.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (l.is_international_academy= " + "'" + keyword4 + "'"
					+ " OR l.company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public double getWorksheetTradeAmount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		Double result;
		String sql = " SELECT SUM(Zongjia) FROM( "
								+" SELECT "
								+" d.ID,d.ShangpinID,d.RukuSj,d.Zongjia,s.FabuzheID "
								+" FROM dingdan d "
								+" RIGHT JOIN shangpin s ON d.ShangpinID = s.ID "
								+" WHERE s.Leixing = 'jiagongdan' "
								+" AND s.FabuzheLx = 'shanghu' "
								+" ) t1 "
								+" LEFT JOIN loginkehu l ON t1.FabuzheID = l.ID ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (t1.RukuSj> " + "'" + keyword1 + "'" + "AND t1.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
				+ " OR l.belong= " + "'" + keyword3 + "'" 
				+ " OR l.country= " + "'" + keyword3 + "'" 
				+ " OR l.province= "+ "'" + keyword3 + "'"
				+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (l.is_international_academy= " + "'" + keyword4 + "'"
				+ " OR l.company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		result = jdbcTemplate.queryForObject(sql, Double.class);
		return result == null ? 0 : result;
	}
	
	@Override
	public int getBuyoutOrderCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM( "
									+" SELECT "
									+" d.ID,d.ShangpinID,d.RukuSj,d.Zongjia,s.FabuzheID "
									+" FROM dingdan d "
									+" RIGHT JOIN shangpin s ON d.ShangpinID = s.ID "
									+" WHERE s.Leixing = 'shejixiu' "
									+" AND s.FabuzheLx = 'shanghu' "
									+") t1 "
									+" LEFT JOIN loginkehu l ON t1.FabuzheID = l.ID ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (t1.RukuSj> " + "'" + keyword1 + "'" + "AND t1.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
			+ " OR l.belong= " + "'" + keyword3 + "'" 
			+ " OR l.country= " + "'" + keyword3 + "'" 
			+ " OR l.province= "+ "'" + keyword3 + "'"
			+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (l.is_international_academy= " + "'" + keyword4 + "'"
			+ " OR l.company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public double getBuyoutTradeAmount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		Double result;
		String sql = " SELECT SUM(Zongjia) FROM( "
				+" SELECT "
				+" d.ID,d.ShangpinID,d.RukuSj,d.Zongjia,s.FabuzheID "
				+" FROM dingdan d "
				+" RIGHT JOIN shangpin s ON d.ShangpinID = s.ID "
				+" WHERE s.Leixing = 'shejixiu' "
				+" AND s.FabuzheLx = 'shanghu' "
				+") t1 "
				+" LEFT JOIN loginkehu l ON t1.FabuzheID = l.ID ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (t1.RukuSj> " + "'" + keyword1 + "'" + "AND t1.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (l.is_international_academy= " + "'" + keyword4 + "'"
					+ " OR l.company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		result = jdbcTemplate.queryForObject(sql, Double.class);
		return result == null ? 0 : result;
	}
	
	@Override
	public double getDesignerTradeAmount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		Double result;
		String sql = " SELECT SUM(Zongjia) FROM dingdan d "
				+ " RIGHT JOIN loginkehu l ON d.MmaijiaID = l.ID "
				+ " WHERE d.Zhuangtai IN (4, 8, 16, 32, 64, 128, 256) "
				+ " AND d.MmaijiaLx = 'shanghu' ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (d.RukuSj> " + "'" + keyword1 + "'" + "AND d.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
					+ " OR company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		result = jdbcTemplate.queryForObject(sql, Double.class);
		return result == null ? 0 : result;
	}
	
	@Override
	public int getDesignerBillCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM shangpin s "
				+ " RIGHT JOIN loginkehu l ON s.FabuzheID = l.ID "
				+ " WHERE s.FabuzheLx = 'shanghu' ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (s.RukuSj> " + "'" + keyword1 + "'" + "AND s.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (l.is_international_academy= " + "'" + keyword4 + "'"
					+ " OR l.company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public int getUserBillCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM shangpin s "
				+ "RIGHT JOIN loginkehu l ON s.FabuzheID = l.ID "
				+ " WHERE s.FabuzheLx = 'kehu' ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (s.RukuSj> " + "'" + keyword1 + "'" + "AND s.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (l.is_international_academy= " + "'" + keyword4 + "'"
					+ " OR l.company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}
	
	@Override
	public int getUserSuccessBillCount(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = " SELECT COUNT(*) FROM dingdan d "
				+ " RIGHT JOIN loginkehu l ON d.MmaijiaID = l.ID "
				+ " WHERE d.MmaijiaLx = 'kehu' ";
		if(keyword1.length() > 0 && keyword2.length() > 0){
			sql += " AND (d.RukuSj> " + "'" + keyword1 + "'" + "AND d.RukuSj< " + "'" + keyword2 + "'" + " ) ";
		}
		if(keyword3.length() > 0){
			sql += " AND (l.identity= " + "'" + keyword3 + "'"
					+ " OR l.belong= " + "'" + keyword3 + "'" 
					+ " OR l.country= " + "'" + keyword3 + "'" 
					+ " OR l.province= "+ "'" + keyword3 + "'"
					+ " OR l.city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(keyword4.length() > 0){
			sql += " AND (l.is_international_academy= " + "'" + keyword4 + "'"
					+ " OR l.company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}

	@Override
	public int getOnlineUserCount() {
		// TODO Auto-generated method stub
		return AppCtrl.tokenMap.size();
	}

	@Override
	public int getNewUserCount(String keyword1, String keyword2,
			String keyword3, String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		boolean flag1 = keyword1.length() > 0 && keyword2.length() > 0;
		boolean flag2 = keyword3.length() > 0;
		boolean flag3 = keyword4.length() > 0;
		String sql = " SELECT COUNT(*) FROM loginkehu ";
		if(flag1){
			sql += " WHERE(create_time> " +  "'" + keyword1 + "'" + " AND create_time< " + "'" + keyword2 + "'" + " ) ";
		}
		if(flag2){
			sql += " AND (identity= " + "'" + keyword3 + "'"
					 	+ " OR belong= " + "'" + keyword3 + "'" 
						+ " OR country= " + "'" + keyword3 + "'" 
						+ " OR province= "+ "'" + keyword3 + "'"
						+ " OR city= " + "'" + keyword3 + "'" + " ) ";
		}
		if(flag3){
			sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
					+ " OR company_type= " + "'" + keyword4 + "'" + " ) ";
		}
		num = jdbcTemplate.queryForObject(sql, Integer.class);
		return num;
	}

	@Override
	public int getLoseUserCount(String keyword1, String keyword2,
			String keyword3, String keyword4) {
		// TODO Auto-generated method stub
		int num = 0;
		String sql = "";
		if(keyword1.length() > 0 || keyword3.length() > 0 || keyword4.length() > 0){
			if (keyword1.length() > 0) {
				sql = " SELECT COUNT(*) FROM loginkehu l "
						+ " WHERE (DATE_ADD(l.last_login_time, INTERVAL 7 DAY) < "
						+ "'" + keyword1 + "'" + " ) ";
			} 
			if (keyword3.length() > 0) {
				sql += " AND (identity= " + "'" + keyword3 + "'"
					 	+ " OR belong= " + "'" + keyword3 + "'" 
						+ " OR country= " + "'" + keyword3 + "'" 
						+ " OR province= "+ "'" + keyword3 + "'"
						+ " OR city= " + "'" + keyword3 + "'" + " ) ";
			} 
			if (keyword4.length() > 0) {
				sql += " AND (is_international_academy= " + "'" + keyword4 + "'"
						+ " OR company_type= " + "'" + keyword4 + "'" + " ) ";
			}
			num = jdbcTemplate.queryForObject(sql, Integer.class);
		}
		return num;
	}
	
	@Override
	public boolean isAllNull(String keyword1, String keyword2, String keyword3,
			String keyword4) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(keyword1.length() == 0 && keyword2.length() == 0 && keyword3.length() == 0 && keyword4.length() == 0){
			flag = true;
		}
		return flag;
	}
	
}
