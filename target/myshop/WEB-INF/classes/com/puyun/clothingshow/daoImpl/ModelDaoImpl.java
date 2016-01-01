package com.puyun.clothingshow.daoImpl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.puyun.clothingshow.dao.ModelDao;
import com.puyun.clothingshow.entity.GoodsType;
import com.puyun.clothingshow.entity.Model;
import com.puyun.clothingshow.entity.MouldForApp;
import com.puyun.clothingshow.entity.MuBanTupian;

/**
 * 模板管理相关数据访问接口实现类
 */
public class ModelDaoImpl implements ModelDao {
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
	public List<Model> getModelList(String keyword, int start, int num) {
		// TODO Auto-generated method stub
		String key = "%" + keyword + "%";
		String sql = "select * from muban where Name like ? order by ID desc" + " limit "
				+ start + "," + num;
		List<Model> list = jdbcTemplate.query(sql, new Object[] { key },
				new BeanPropertyRowMapper<Model>(Model.class));
		for(Model model : list){
			String sql1 = "select * from mubantupian where MubanID=?";
			List<MuBanTupian> photoList = jdbcTemplate.query(sql1, new Object[] { model.getID() },
					new BeanPropertyRowMapper<MuBanTupian>(MuBanTupian.class));
			model.setUrl_list(photoList);
			String sql2 = "select * from zhizuoleixing where ID=?";
			List<GoodsType> madeTypeList = jdbcTemplate.query(sql2, new Object[] { model.getZhizuoLxID() },
					new BeanPropertyRowMapper<GoodsType>(GoodsType.class));
			if(madeTypeList.size() > 0){
				model.setZhizuolx(madeTypeList.get(0).getName());
			}
		}
		return list;
	}

	@Override
	public int getModelCount(String keyword) {
		// TODO Auto-generated method stub
		String key = "%" + keyword + "%";
		String sql = "select count(*) from muban where Name like ?";
		@SuppressWarnings("deprecation")
		int num = jdbcTemplate.queryForInt(sql, key);
		return num;
	}

	@Override
	public boolean addModel(Model model, String[] photoName, String[] photoUrl, String[] photoType) {
		// TODO Auto-generated method stub
		boolean flag = false;
        String sql1 = "insert into muban(description,ZhizuoLxID,Name,Jiage)" + "values(:description,:ZhizuoLxID,:Name,:Jiage)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        this.namedJdbcTemplate.update(sql1, new BeanPropertySqlParameterSource(model), generatedKeyHolder);
        int modelId = generatedKeyHolder.getKey().intValue();
        for(int i = 0; i < photoName.length; i ++){
        	String sql2 = "insert into mubantupian(MubanID,Url,TupianLx,IsGongkai,JubaoZt,JubaoCs,ShanchuZt,RukuSj,TupianMc)"
            		+ "values(?,?,?,1,0,0,0,Now(),?)";
        	int num = jdbcTemplate.update(sql2, modelId,photoUrl[i],photoType[i],photoName[i]);
        	flag = num > 0;
        }
        
        return flag;
	}

}
