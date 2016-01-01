package com.puyun.clothingshow.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.puyun.clothingshow.dao.CheckDao;
import com.puyun.clothingshow.dao.GoodsTypeDao;
import com.puyun.clothingshow.entity.Buyer;
import com.puyun.clothingshow.entity.Goods;
import com.puyun.clothingshow.entity.GoodsPhoto;
import com.puyun.clothingshow.entity.GoodsType;
import com.puyun.clothingshow.entity.MadeType;
import com.puyun.clothingshow.entity.MuBanTupian;
import com.puyun.clothingshow.entity.PhotoBean;
import com.puyun.clothingshow.entity.Saler;
import com.puyun.clothingshow.entity.UserAvatar;
import com.puyun.clothingshow.entity.UserType;

/**
 * 商品类型管理相关数据访问接口实现类
 * 
 * @author ldz 创建时间: 2015-2-10
 */
public class GoodsTypeDaoImpl implements GoodsTypeDao {
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
	public List<GoodsType> getGoodsTypeList(String keyword, int start, int num) {
		// TODO Auto-generated method stub
		String key = "%" + keyword + "%";
		String sql = "select ID,Name,Nanduxishu as X from zhizuoleixing where ID != 0 AND Name like ?" + " limit " + start + "," + num;
		List<GoodsType> list = jdbcTemplate.query(sql, new Object[] {key},
				new BeanPropertyRowMapper<GoodsType>(GoodsType.class));
		return list;
	}

	@Override
	public int getGoodsTypeListCount(String keyword) {
		// TODO Auto-generated method stub
		String key = "%" + keyword + "%";
		String sql = "select count(*) from zhizuoleixing where ID != 0 AND Name like ?";
		@SuppressWarnings("deprecation")
		int num = jdbcTemplate.queryForInt(sql, key);
		return num;
	}

	@Override
	public boolean addGoodsType(GoodsType goodsType) {
		// TODO Auto-generated method stub
        String sql =" insert into zhizuoleixing(Name,Nanduxishu) values(:name,:X) ";
        int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(goodsType));
        return num > 0;
	}
	
	@Override
	public GoodsType getGoodsTypeDetail(int id) {
		// TODO Auto-generated method stub
		String sql = "select ID,Name,Nanduxishu as X from zhizuoleixing where ID=?";
		List<GoodsType> list = jdbcTemplate.query(sql, new Object[] {id},
				new BeanPropertyRowMapper<GoodsType>(GoodsType.class));
		return list.size() > 0 ? list.get(0) : null;
	}
	
	@Override
	public boolean updateGoodsType(GoodsType goodsType) {
		// TODO Auto-generated method stub
		String sql = "UPDATE zhizuoleixing SET Name=:name,Nanduxishu=:X where ID=:id";
		int num = namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(goodsType));
		return num > 0;
	}

}
