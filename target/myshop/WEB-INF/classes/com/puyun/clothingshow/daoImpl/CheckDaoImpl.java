package com.puyun.clothingshow.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.puyun.clothingshow.dao.CheckDao;
import com.puyun.clothingshow.entity.Buyer;
import com.puyun.clothingshow.entity.Goods;
import com.puyun.clothingshow.entity.GoodsPhoto;
import com.puyun.clothingshow.entity.MadeType;
import com.puyun.clothingshow.entity.MuBanTupian;
import com.puyun.clothingshow.entity.PhotoBean;
import com.puyun.clothingshow.entity.Saler;
import com.puyun.clothingshow.entity.UserAvatar;
import com.puyun.clothingshow.entity.UserType;

/**
 * 审核管理相关数据访问接口实现类
 * 
 * @author ldz 创建时间: 2015-2-10
 */
public class CheckDaoImpl implements CheckDao {
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
	public List<Goods> getClothesList(String keyword, int start, int num) {
		String key = "%" + keyword + "%";
		String sql = "select * from shangpin where Leixing='chengyixiu' and (Mingcheng like ?)"
				+ " order by IsXiajia desc,RukuSj desc" + " limit " + start + "," + num;
		List<Goods> list = jdbcTemplate.query(sql, new Object[] { key },
				new BeanPropertyRowMapper<Goods>(Goods.class));
		for (Goods goods : list) {
			String sql1 = "select * from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getId() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			goods.setUrl_list(photoList);
			String sql2 = "";
			if (UserType.BUYER.getValue().equals(goods.getFabuzhelx())) {
				sql2 = "select UserName as name from LoginKehu where ID=?";
				List<Buyer> buyerList = jdbcTemplate.query(sql2,
						new Object[] { goods.getFabuzheid() },
						new BeanPropertyRowMapper<Buyer>(Buyer.class));
				if (buyerList.size() > 0) {
					Buyer buyer = buyerList.get(0);
					goods.setUsername(buyer.getName());
				}
			} else {
				sql2 = "select UserName as name from LoginShanghu where ID=?";
				List<Saler> salerList = jdbcTemplate.query(sql2,
						new Object[] { goods.getFabuzheid() },
						new BeanPropertyRowMapper<Saler>(Saler.class));
				if (salerList.size() > 0) {
					Saler saler = salerList.get(0);
					goods.setUsername(saler.getName());
				}
			}
			String sql3 = "select Name from zhizuoleixing where ID=?";
			List<MadeType> madeList = jdbcTemplate.query(sql3,
					new Object[] { goods.getZhizuolx() },
					new BeanPropertyRowMapper<MadeType>(MadeType.class));
			if (madeList.size() > 0) {
				goods.setZhizuolxname(madeList.get(0).getName());
			}
		}

		return list;
	}

	@Override
	public int getClothesListCount(String keyword) {
		String key = "%" + keyword + "%";
		String sql = "select * from shangpin where Leixing='chengyixiu' and (Mingcheng like ?)"
				+ " order by IsXiajia desc,RukuSj desc";
		@SuppressWarnings("deprecation")
		int num = jdbcTemplate.queryForInt("select count(*) from(" + sql
				+ ") t", key);
		return num;
	}

	@Override
	public Map<String, Object> getClothesCount() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		int allCount = jdbcTemplate
				.queryForInt("select count(*) from shangpin where Leixing='chengyixiu'");
		int soldOutCount = jdbcTemplate
				.queryForInt("select count(*) from shangpin where Leixing='chengyixiu' and IsXiajia=1");
		map.put("allCount", allCount);
		map.put("soldOutCount", soldOutCount);
		return map;
	}

	@Override
	public Goods getClothesDetail(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from shangpin where ID=? ";
		List<Goods> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<Goods>(Goods.class));
		Goods goods = null;
		if (list.size() > 0) {
			goods = list.get(0);
			String sql1 = "select * from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getId() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			goods.setUrl_list(photoList);
			String sql2 = "";
			if (UserType.BUYER.getValue().equals(goods.getFabuzhelx())) {
				sql2 = "select UserName as name from LoginKehu where ID=?";
				List<Buyer> buyerList = jdbcTemplate.query(sql2,
						new Object[] { goods.getFabuzheid() },
						new BeanPropertyRowMapper<Buyer>(Buyer.class));
				if (buyerList.size() > 0) {
					Buyer buyer = buyerList.get(0);
					goods.setUsername(buyer.getName());
				}
			} else {
				sql2 = "select UserName as name from LoginShanghu where ID=?";
				List<Saler> salerList = jdbcTemplate.query(sql2,
						new Object[] { goods.getFabuzheid() },
						new BeanPropertyRowMapper<Saler>(Saler.class));
				if (salerList.size() > 0) {
					Saler saler = salerList.get(0);
					goods.setUsername(saler.getName());
				}
			}
			String sql3 = "select Name from zhizuoleixing where ID=?";
			List<MadeType> madeList = jdbcTemplate.query(sql3,
					new Object[] { goods.getZhizuolx() },
					new BeanPropertyRowMapper<MadeType>(MadeType.class));
			if (madeList.size() > 0) {
				goods.setZhizuolxname(madeList.get(0).getName());
			}
		}
		return goods;
	}

	@Override
	public boolean updateClothesStatus(int id, int status) {
		// TODO Auto-generated method stub
		String statusStr = "";
		//下架操作
		if (status == 0) {
			statusStr = "1";
		//上架操作
		} else {
			statusStr = "0";
		}
		String sql = "update shangpin set IsXiajia=? where ID=?";
		int num = jdbcTemplate.update(sql, new Object[] { statusStr, id });
		return num > 0 ? true : false;
	}

	@Override
	public List<MadeType> getMadeTypeList() {
		// TODO Auto-generated method stub
		String sql = "select * from zhizuoleixing where id != '0'";
		List<MadeType> madeList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<MadeType>(MadeType.class));
		return madeList;
	}

	@Override
	public boolean modifyMadeType(int id, int type) {
		// TODO Auto-generated method stub
		String sql = "update shangpin set ZhizuoLx=? where ID=?";
		int num = jdbcTemplate.update(sql, new Object[] { type, id });
		return num > 0 ? true : false;
	}

	@Override
	public List<PhotoBean> getPhotoList(int start, int num) {
		// TODO Auto-generated method stub
		List<PhotoBean> list = new ArrayList<PhotoBean>();
		String sql1 = "select ID as id,YonghuID as userId,Url as url,JubaoZt as report_status,JubaoCs as report_count, "
				+ " ShanchuZt as delete_status,RukuSj as create_time,TupianMc as tupianmc,YonghuLx as userType from yonghutouxiang where "
				+ " JubaoZt='1' and ShanchuZt='0' "
				+ " ORDER BY RukuSj desc ";
		List<UserAvatar> userAvatarList = jdbcTemplate.query(sql1,
				new BeanPropertyRowMapper<UserAvatar>(UserAvatar.class));
		PhotoBean photo = null;
		for (UserAvatar userAvatar : userAvatarList) {
			photo = new PhotoBean();
			photo.setTypeId(Integer.valueOf(userAvatar.getUserId()));
			photo.setId(Integer.valueOf(userAvatar.getId()));
			if (UserType.BUYER.getValue().equals(userAvatar.getUserType())) {
				String usernamesql = "select ID,UserName as name from loginkehu where ID=?";
				List<Buyer> buyerList = jdbcTemplate.query(usernamesql,
						new Object[] { userAvatar.getUserId() },
						new BeanPropertyRowMapper<Buyer>(Buyer.class));
				if (buyerList.size() > 0) {
					photo.setPublisher(buyerList.get(0).getName());
					photo.setPublisherId(Integer.valueOf(buyerList.get(0).getId()));
					photo.setPublisherType(0);
				}
				photo.setType(1);
			} else {
				String usernamesql = "select ID,UserName as name from loginshanghu where ID=?";
				List<Saler> salerList = jdbcTemplate.query(usernamesql,
						new Object[] { userAvatar.getUserId() },
						new BeanPropertyRowMapper<Saler>(Saler.class));
				if (salerList.size() > 0) {
					photo.setPublisher(salerList.get(0).getName());
					photo.setPublisherId(Integer.valueOf(salerList.get(0).getId()));
					photo.setPublisherType(1);
				}
				photo.setType(2);
			}
			photo.setUrl(userAvatar.getUrl());
			photo.setJubaozt(userAvatar.getReport_status());
			photo.setJubaocs(userAvatar.getReport_count());
			photo.setShanchuzt(userAvatar.getDelete_status());
			photo.setRukusj(userAvatar.getCreate_time());
			photo.setTupianmc(userAvatar.getTupianmc());
			list.add(photo);
		}
		String sql2 = "select * from shangpintupian where JubaoZt=1 and ShanchuZt=0";
		List<GoodsPhoto> goodsPhotoList = jdbcTemplate.query(sql2,
				new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
		for (GoodsPhoto goodsPhoto : goodsPhotoList) {
			photo = new PhotoBean();
			photo.setTypeId(Integer.valueOf(goodsPhoto.getShangpinId()));
			photo.setType(3);
			photo.setId(Integer.valueOf(goodsPhoto.getId()));
			photo.setUrl(goodsPhoto.getUrl());
			photo.setJubaozt(goodsPhoto.getJubaoZt());
			photo.setJubaocs(goodsPhoto.getJubaoCs());
			photo.setShanchuzt(goodsPhoto.getShanChuZt());
			photo.setIsgongkai(goodsPhoto.getIsGongkai());
			photo.setRukusj(goodsPhoto.getRuKuSj());
			photo.setTupianmc(goodsPhoto.getTupianMc());
			String goodsql = "select FabuzheLx,FabuzheID,Mingcheng from shangpin where ID=?";
			List<Goods> goodsList = jdbcTemplate.query(goodsql,
					new Object[] { goodsPhoto.getShangpinId() },
					new BeanPropertyRowMapper<Goods>(Goods.class));
			if (goodsList.size() > 0) {
				Goods goods = goodsList.get(0);
				photo.setGoodsname(goods.getMingcheng());
				if (UserType.BUYER.getValue().equals(goods.getFabuzhelx())) {
					String usernamesql = "select ID,UserName as name from loginkehu where ID=?";
					List<Buyer> buyerList = jdbcTemplate.query(usernamesql,
							new Object[] { goods.getFabuzheid() },
							new BeanPropertyRowMapper<Buyer>(Buyer.class));
					if (buyerList.size() > 0) {
						photo.setPublisher(buyerList.get(0).getName());
						photo.setPublisherId(Integer.valueOf(buyerList.get(0).getId()));
						photo.setPublisherType(0);
					}
				} else {
					String usernamesql = "select ID,UserName as name from loginshanghu where ID=?";
					List<Saler> salerList = jdbcTemplate.query(usernamesql,
							new Object[] { goods.getFabuzheid() },
							new BeanPropertyRowMapper<Saler>(Saler.class));
					if (salerList.size() > 0) {
						photo.setPublisher(salerList.get(0).getName());
						photo.setPublisherId(Integer.valueOf(salerList.get(0).getId()));
						photo.setPublisherType(1);
					}
				}
			}
			list.add(photo);
		}
		String sql3 = "select * from mubantupian where JubaoZt=1 and ShanchuZt=0";
		List<MuBanTupian> modelList = jdbcTemplate.query(sql3,
				new BeanPropertyRowMapper<MuBanTupian>(MuBanTupian.class));
		for (MuBanTupian model : modelList) {
			photo = new PhotoBean();
			photo.setTypeId(Integer.valueOf(model.getMubanID()));
			photo.setType(4);
			photo.setId(Integer.valueOf(model.getID()));
			photo.setUrl(model.getUrl());
			photo.setJubaozt(Integer.valueOf(model.getJubaoZt()));
			photo.setJubaocs(Integer.valueOf(model.getJubaoCs()));
			photo.setShanchuzt(Integer.valueOf(model.getShanChuZt()));
			photo.setIsgongkai(Integer.valueOf(model.getIsGongkai()));
			photo.setRukusj(model.getRukuSj());
			photo.setTupianmc(model.getTupianMc());
			photo.setTupianlx(model.getTupianLx());
			list.add(photo);
		}
		
//		Object[] beanObjects = list.toArray();  
//        Arrays.sort(beanObjects);
//        for (int i = 0; i < beanObjects.length; i++) {  
//            System.out.println(beanObjects[i]);  
//        }
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getPhotoListCount() {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from yonghutouxiang where JubaoZt='1'";
		int userAvatarCount = jdbcTemplate.queryForInt(sql1);
		String sql2 = "select count(*) from shangpintupian where JubaoZt='1'";
		int goodsPhotoCount = jdbcTemplate.queryForInt(sql2);
		String sql3 = "select count(*) from mubantupian where JubaoZt='1'";
		int modelPhotoCount = jdbcTemplate.queryForInt(sql3);
		return userAvatarCount + goodsPhotoCount + modelPhotoCount;
	}
	
	@Override
    public boolean deletePhoto(int type, int id) {
		String sql = "";
		int num;
		switch (type) {
		case 1:
		case 2:
			sql = "update yonghutouxiang set ShanchuZt=1 where ID=" + id;
			num = jdbcTemplate.update(sql);
	        return num > 0;
		case 3:
			sql = "update shangpintupian set ShanchuZt=1 where ID=" + id;
			num = jdbcTemplate.update(sql);
	        return num > 0;
		case 4:
			sql = "update mubantupian set ShanchuZt=1 where ID=" + id;
			num = jdbcTemplate.update(sql);
	        return num > 0;
		}
		return false;
    }

	@Override
	public Map<String, Object> getWarnInfo(int type, int id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "";
		switch (type) {
		case 1:
		case 2:
			sql = "select YonghuID as userId, YonghuLx as userType from yonghutouxiang where ID=" + id;
			List<UserAvatar> userAvatarList = jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<UserAvatar>(UserAvatar.class));
			if(userAvatarList.size() > 0){
				UserAvatar avatar = userAvatarList.get(0);
				map.put("publisherId", avatar.getUserId());
				map.put("publisherType", avatar.getUserType());
			}
			break;
		case 3:
			String sql2 = "select * from shangpintupian where JubaoZt=1 and ShanchuZt=0";
			List<GoodsPhoto> goodsPhotoList = jdbcTemplate.query(sql2,
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			for (GoodsPhoto goodsPhoto : goodsPhotoList) {
				String goodsql = "select FabuzheLx,FabuzheID,Mingcheng from shangpin where ID=?";
				List<Goods> goodsList = jdbcTemplate.query(goodsql,
						new Object[] { goodsPhoto.getShangpinId() },
						new BeanPropertyRowMapper<Goods>(Goods.class));
				if (goodsList.size() > 0) {
					Goods goods = goodsList.get(0);
					
					if (UserType.BUYER.getValue().equals(goods.getFabuzhelx())) {
						String usernamesql = "select ID,UserName as name from loginkehu where ID=?";
						List<Buyer> buyerList = jdbcTemplate.query(usernamesql,
								new Object[] { goods.getFabuzheid() },
								new BeanPropertyRowMapper<Buyer>(Buyer.class));
						if (buyerList.size() > 0) {
							map.put("publisherId", Integer.valueOf(buyerList.get(0).getId()));
							map.put("publisherType", goods.getFabuzhelx());
						}
					} else {
						String usernamesql = "select ID,UserName as name from loginshanghu where ID=?";
						List<Saler> salerList = jdbcTemplate.query(usernamesql,
								new Object[] { goods.getFabuzheid() },
								new BeanPropertyRowMapper<Saler>(Saler.class));
						if (salerList.size() > 0) {
							map.put("publisherId", Integer.valueOf(salerList.get(0).getId()));
							map.put("publisherType", goods.getFabuzhelx());
						}
					}
				}
			}
			break;
		case 4:
			break;
		}
		return map;
	}
}
