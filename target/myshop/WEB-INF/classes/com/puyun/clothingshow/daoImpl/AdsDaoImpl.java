package com.puyun.clothingshow.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.puyun.clothingshow.dao.AdsDao;
import com.puyun.clothingshow.dao.CheckDao;
import com.puyun.clothingshow.dao.GoodsTypeDao;
import com.puyun.clothingshow.entity.Ads;
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
 * 广告位管理相关数据访问接口实现类
 */
public class AdsDaoImpl implements AdsDao {
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
	public List<Ads> getAdsList(String keyword, int start, int num) {
		// TODO Auto-generated method stub
		String key = "%" + keyword + "%";
		String sql = "select * from laad where Title like ? order by ID desc" + " limit "
				+ start + "," + num;
		List<Ads> list = jdbcTemplate.query(sql, new Object[] { key },
				new BeanPropertyRowMapper<Ads>(Ads.class));
		for (Ads ad : list) {
			String sql2 = "select UserName from loginshanghu where ID=?";
			List<Saler> salerList = jdbcTemplate.query(sql2,
					new Object[] { ad.getUrl() },
					new BeanPropertyRowMapper<Saler>(Saler.class));
			if (salerList.size() > 0) {
				ad.setUsername(salerList.get(0).getName());
			}
		}
		return list;
	}

	@Override
	public int getAdsListCount(String keyword) {
		// TODO Auto-generated method stub
		String key = "%" + keyword + "%";
		String sql = "select count(*) from laad where Title like ?";
		@SuppressWarnings("deprecation")
		int num = jdbcTemplate.queryForInt(sql, key);
		return num;
	}

	@Override
	public boolean addAds(Ads ad) {
		// TODO Auto-generated method stub
		String sql = "insert into laad(Title,Types,Shuoming,Tupian,Url,Code,Point,IsValue)"
				+ "values(:Title,'1',:Shuoming,:Tupian,:Url,0,'index',:IsValue)";
		int num = namedJdbcTemplate.update(sql,
				new BeanPropertySqlParameterSource(ad));
		return num > 0;
	}

	@Override
	public boolean isExist(String tel) {
		// TODO Auto-generated method stub
		String sql = "SELECT count(*) FROM loginshanghu WHERE LoginID IN (SELECT ID FROM login WHERE Tel = ?)";
		int num = jdbcTemplate.queryForInt(sql, new Object[] { tel });
		return num > 0;
	}
	
	@Override
	public String getSalerId(String tel) {
		// TODO Auto-generated method stub
		String id = "";
		String sql = "SELECT ID FROM loginshanghu WHERE LoginID IN (SELECT ID FROM login WHERE Tel = ?)";
		List<Saler> salerList = jdbcTemplate.query(sql,
				new Object[] {tel},
				new BeanPropertyRowMapper<Saler>(Saler.class));
		if (salerList.size() > 0) {
			id = salerList.get(0).getId();
		}
		return id;
	}
}
