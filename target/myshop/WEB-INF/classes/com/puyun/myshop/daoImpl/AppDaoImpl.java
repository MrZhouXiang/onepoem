package com.puyun.myshop.daoImpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.puyun.myshop.base.Constants;
import com.puyun.myshop.base.DateUtil;
import com.puyun.myshop.base.util.Utils;
import com.puyun.myshop.ctrl.AppCtrl;
import com.puyun.myshop.dao.AppDao;
import com.puyun.myshop.entity.Address;
import com.puyun.myshop.entity.Ads;
import com.puyun.myshop.entity.Apk;
import com.puyun.myshop.entity.Buyer;
import com.puyun.myshop.entity.BuyerForOrder;
import com.puyun.myshop.entity.ColorForApp;
import com.puyun.myshop.entity.Comment;
import com.puyun.myshop.entity.Express;
import com.puyun.myshop.entity.GoodsForApp;
import com.puyun.myshop.entity.GoodsPhoto;
import com.puyun.myshop.entity.GoodsType;
import com.puyun.myshop.entity.HistoryShowForApp;
import com.puyun.myshop.entity.LVBean;
import com.puyun.myshop.entity.MouldForApp;
import com.puyun.myshop.entity.MuBanTupian;
import com.puyun.myshop.entity.OrderForApp;
import com.puyun.myshop.entity.PhotoBean;
import com.puyun.myshop.entity.PoemMod;
import com.puyun.myshop.entity.Saler;
import com.puyun.myshop.entity.StandardSize;
import com.puyun.myshop.entity.User;
import com.puyun.myshop.entity.UserAvatar;
import com.puyun.myshop.entity.UserSize;
import com.puyun.myshop.entity.UserType;

/**
 * 衣氏百秀App接口实现类
 */
public class AppDaoImpl extends BaseDaoImpl implements AppDao {
	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	private static final Logger logger = Logger.getLogger(AppDaoImpl.class);

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(
				jdbcTemplate.getDataSource());
	}

	@Override
	public User login(String tel, String password) {
		String sql = "select Id,Tel,LoginName,Password from login where LoginName=? and Password=?";
		List<User> list = jdbcTemplate.query(sql,
				new Object[] { tel, password },
				new BeanPropertyRowMapper<User>(User.class));
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public int register(User user) {
		String sql1 = "insert into login(LoginName,Tel,Password) "
				+ "values(:LoginName,:Tel,:Password)";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		this.namedJdbcTemplate.update(sql1, new BeanPropertySqlParameterSource(
				user), generatedKeyHolder);
		int loginId = generatedKeyHolder.getKey().intValue();
		return loginId;
	}

	@Override
	public int updatePwd(String account, String newPwd) {
		String sql = "update login set Password=?  where LoginName=?";
		int num = jdbcTemplate.update(sql, newPwd, account);
		return num;
	}

	@Override
	public User getUser(String tel) {
		String sql = "select Id,Tel,LoginName from Login where LoginName=?";
		List<User> list = jdbcTemplate.query(sql, new Object[] { tel },
				new BeanPropertyRowMapper<User>(User.class));
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Apk getApk() {
		String sql = "select  apkid,url,version_code as versionCode,version_name as versionName,version_desc as versionDesc,forced_update as forcedUpdate "
				+ "from t_apk t  order by apkid desc limit 1";
		List<Apk> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<Apk>(Apk.class));
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public String getBuyerId(String loginId) {
		String buyerId = "";
		String sql = "select id from LoginKehu where LoginId=?";
		List<Buyer> list = jdbcTemplate.query(sql, new Object[] { loginId },
				new BeanPropertyRowMapper<Buyer>(Buyer.class));
		if (list.size() > 0) {
			Buyer buyer = list.get(0);
			buyerId = buyer.getId();
		}
		return buyerId;
	}

	@Override
	public String getSalerId(String loginId) {
		String salerId = "";
		String sql = "select id from LoginShanghu where LoginId=?";
		List<Saler> list = jdbcTemplate.query(sql, new Object[] { loginId },
				new BeanPropertyRowMapper<Saler>(Saler.class));
		if (list.size() > 0) {
			Saler saler = list.get(0);
			salerId = saler.getId();
		}
		return salerId;
	}

	@Override
	public Buyer getBuyerInfo(String id) {
		String sql = "SELECT lk.ID AS id, " + "lk.UserName AS NAME, "
				+ "lk.TouxiangID, " + "lk.Dengji AS lv, "
				+ "lk.weiboName AS weibo_name, "
				+ "lk.weixinName AS weixin_name, " + "lk.NameCs AS NameCs, "
				+ "l.Tel AS tel " + "FROM " + "loginkehu lk "
				+ "RIGHT JOIN login l ON lk.LoginID = l.ID " + "WHERE "
				+ "lk.ID = ? ";
		List<Buyer> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<Buyer>(Buyer.class));
		Buyer buyer = null;
		if (list.size() > 0) {
			buyer = list.get(0);
			String url = getUserAvatarUrl(buyer.getId(), "kehu");
			if (url != null) {
				buyer.setUrl(url);
			}
			UserAvatar url2 = getUserAvatarUrl2(buyer.getId(), "kehu");
			if (url != null) {
				buyer.setUrl2(url2);
			}
		}

		String sql1 = "select ID as id,loginID,name,Shengao as height,Tizhong as weight,Chima as normal_size,Yaowei as waist,Xiongwei as bust,Tunwei as hip,Miaoshu as description from yonghuchima where LoginID=?";
		List<UserSize> size_list = jdbcTemplate.query(sql1,
				new Object[] { id }, new BeanPropertyRowMapper<UserSize>(
						UserSize.class));
		if (buyer != null) {
			buyer.setSize_list(size_list);
		}
		return buyer;
	}

	@Override
	public Saler getSalerInfo(String id) {
		String sql = "SELECT a.ID AS id, a.UserName AS NAME, a.TouxiangID, a.Dengji AS lv, a.Jingyan AS jingyan, a.weiboName AS weibo_name, a.weixinName AS weixin_name,a.NameCs as NameCs,"
				+ "a.ZhifuZhanghu AS zhifuzhanghu,a.bank_card_no,a.issuing_bank,b.Tel AS tel FROM LoginShanghu AS a RIGHT JOIN login AS b ON a.LoginID = b.ID WHERE a.ID = ?";

		List<Saler> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<Saler>(Saler.class));
		Saler saler = null;
		if (list.size() > 0) {
			saler = list.get(0);
			if (saler == null) {
				return null;
			}
			String url = getUserAvatarUrl(saler.getId(), "shanghu");
			if (url != null) {
				saler.setUrl(url);
			}
			UserAvatar url2 = getUserAvatarUrl2(saler.getId(), "shanghu");
			if (url != null) {
				saler.setUrl2(url2);
			}
		}
		if (saler == null) {
			return null;
		}
		String sql1 = "select * from shouhuodizhi where YonghuID=? and YonghuLx='shanghu'";
		List<Address> addressList = jdbcTemplate.query(sql1,
				new Object[] { id }, new BeanPropertyRowMapper<Address>(
						Address.class));
		if (addressList.size() > 0) {
			String dizhi = addressList.get(0).getShouhuodizhi();
			saler.setAddress(dizhi);
		}
		// 设置Saler等级
		saler = setAllLV(saler);
		return saler;
	}

	@Override
	public Saler setAllLV(Saler saler) {
		String sql2 = "SELECT jy.*,lx.`Name` as ZhizuoLxName FROM zhizuojingyan as jy RIGHT JOIN zhizuoleixing as lx ON jy.ZhizuoLxID = lx.ID WHERE YonghuID = ? AND YonghuLx = 'shanghu' ORDER BY jy.ZhizuoLxID ASC";
		List<LVBean> dengji_list = jdbcTemplate.query(sql2,
				new Object[] { saler.getId() },
				new BeanPropertyRowMapper<LVBean>(LVBean.class));
		int dengji_all = 0;
		int jingyan_all = 0;
		int jingyan_temp = 0;
		LVBean LVBean_temp = new LVBean();
		LVBean LVBean_temp2 = new LVBean();

		for (int i = 0; i < dengji_list.size(); i++) {
			LVBean_temp = dengji_list.get(i);
			jingyan_temp = LVBean_temp.getJingyan();
			if (0 != jingyan_temp) {
				LVBean_temp2 = getLVBeanByJingYan(jingyan_temp);
			}
			dengji_list.get(i).setDengji(LVBean_temp2.getDengji());
			dengji_list.get(i).setJingyanMax(LVBean_temp2.getJingyanMax());
			dengji_list.get(i).setY(LVBean_temp2.getY());
		}
		saler.setDengji_list(dengji_list);
		String sql3 = "SELECT jy.*,lx.`Name` as ZhizuoLxName FROM zhizuojingyan as jy RIGHT JOIN zhizuoleixing as lx ON jy.ZhizuoLxID = lx.ID WHERE YonghuID = ? AND YonghuLx = 'shanghu' AND jy.ZhizuoLxID='0'";
		List<LVBean> list = jdbcTemplate.query(sql3,
				new Object[] { saler.getId() },
				new BeanPropertyRowMapper<LVBean>(LVBean.class));
		if (list != null && list.size() > 0) {
			jingyan_all = list.get(0).getJingyan();
		}
		dengji_all = getLvByJingYan(jingyan_all);
		saler.setJingyan(jingyan_all);
		saler.setLv(dengji_all);
		return saler;
	}

	@Override
	public String getUserAvatarUrl(String id, String types) {
		// 查询未删除的图片
		String url = "";
		String sql = "select Url as url from YonghuTouxiang where YonghuID=? and YonghuLx=? and ShanchuZt=0";
		List<UserAvatar> list = jdbcTemplate.query(sql, new Object[] { id,
				types },
				new BeanPropertyRowMapper<UserAvatar>(UserAvatar.class));
		if (list.size() > 0) {
			UserAvatar avatar = list.get(0);
			url = avatar.getUrl();
		}
		return url;
	}

	@Override
	public UserAvatar getUserAvatarUrl2(String id, String types) {
		// 查询未删除的图片
		String sql = "select Url as url,ID as id from YonghuTouxiang where YonghuID=? and YonghuLx=? and ShanchuZt=0";
		List<UserAvatar> list = jdbcTemplate.query(sql, new Object[] { id,
				types },
				new BeanPropertyRowMapper<UserAvatar>(UserAvatar.class));
		UserAvatar avatar = null;
		if (list.size() > 0) {
			avatar = list.get(0);

		}
		return avatar;
	}

	@Override
	public List<GoodsType> getAllGoodsTypes() {
		// 0为额外经验（自由定制，点赞）
		String sql = "select id, name,Nanduxishu as X from ZhizuoLeixing where ID != 0";
		List<GoodsType> list = jdbcTemplate.query(sql, new Object[] {},
				new BeanPropertyRowMapper<GoodsType>(GoodsType.class));
		return list;
	}

	/**
	 * 获取抢单商品
	 * 
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param yonghuid
	 * @param zhizuolx
	 * @return
	 */
	@Override
	public List<GoodsForApp> getGoodsList3(String types, int page_num,
			int page_size, String orderby, int hascomment, int comment_size,
			int yonghuid, int zhizuolx) {
		// 时间
		if ("sj".equals(orderby)) {
			orderby = "RukuSj";
		} else {// 价格
			orderby = "Jiage";

		}
		String zhizuolx_where = "";
		if (zhizuolx == -1) {

		} else {
			zhizuolx_where = "and ZhizuoLx in (" + zhizuolx + ")";
		}
		System.out.println("getGoodsList3-orderby:" + orderby);
		int start = (page_num - 1) * page_size;
		String sql = "select * from shangpin where ((Leixing IN ('shejixiu','jiagongdan','dingzhidan') AND FabuzheLx = 'shanghu') OR (Leixing IN ('chengyixiu') AND FabuzheLx = 'kehu')) "
				+ zhizuolx_where
				+ " and Shuliang>0 and IsXiajia !=1 order by "
				+ orderby + " desc limit " + start + "," + page_size;

		List<GoodsForApp> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<GoodsForApp>(GoodsForApp.class));
		for (GoodsForApp goods : list) {
			String sql1 = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getId() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			if (photoList.size() > 0) {
				// String url = photoList.get(0).getUrl();
				// goods.setUrl(url);
				GoodsPhoto gp = photoList.get(0);
				String url = gp.getUrl();
				goods.setUrl(url);
				/*
				 * if (gp.getIsGongkai() == 1) {// 公开 goods.setUrl(url); } else
				 * {// 不公开 goods.setUrl("暂不公开"); }
				 */
			}
			// 按照10级计算基础价格
			try {
				goods.setBaseJiage(getBaseJiage(goods));
				goods.setX(getNanduxishu(goods.getZhizuoLx()));
			} catch (Exception e) {
				// 抛异常，直接返回原价
				goods.setBaseJiage(goods.getJiage());
				goods.setX(1);
			}

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
		}
		return list;
	}

	/**
	 * 
	 * TODO 计算基础价格, 基础价格=发布价格-基础等级价格.
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年9月17日 下午2:10:14
	 * 
	 * @param goods
	 * @return
	 */
	private float getBaseJiage(GoodsForApp goods) {
		// TODO Auto-generated method stub
		// 基础价格：发布价格-基础等级价格,基础等级价格 = 制作类型难度系数*等级加工费系数^参数系数
		float jiage = goods.getJiage();
		float jiageByLv = getJiageByLv(goods.getZhizuoLx(), 10);
		float basejiage = jiage - jiageByLv;
		return basejiage;
	}

	/**
	 * 基础等级价格 = 制作类型难度系数*等级加工费系数^参数系数 <功能详细描述>
	 * 
	 * @param zhizuoLx
	 * @param lv
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public float getJiageByLv(int zhizuoLx, int lv) {
		float nanduxishu = getNanduxishu(zhizuoLx);
		int jiagongfei = getJiagongfei(lv);
		int a = Constants.AppSysConstans.A;// 此参数应该可以配置
		// java.lang.Math.pow(double a, double b) 返回的是第一个参数的值提高到第二个参数的幂
		// 如: pow(2,3) ---> 8
		float jiageByLv = (float) (nanduxishu * Math.pow(jiagongfei, a));
		return jiageByLv;
	}

	@Override
	public float getNanduxishu(int zhizuoLx) {
		float nanduxishu = 0F;
		// 数据库查出难度系数
		String sql = "SELECT Nanduxishu FROM zhizuoleixing WHERE ID = ?";
		nanduxishu = jdbcTemplate.queryForObject(sql, Float.class,
				new Object[] { zhizuoLx });
		return nanduxishu;
	}

	@Override
	public int getJiagongfei(int lv) {
		// 数据库查出等级加工费系数
		String sql2 = "SELECT Jiagongfei FROM zhizuolxdj WHERE Jishu = ?";
		int jiagongfei = jdbcTemplate.queryForObject(sql2, Integer.class,
				new Object[] { lv });
		System.out.println("jiagongfei:" + jiagongfei);
		return jiagongfei;
	}

	/**
	 * 
	 * TODO 根据类型获取商品列表.(非抢单(客户端使用))
	 * <p>
	 * 方法详细说明,如果要换行请使用<br>
	 * 标签
	 * </p>
	 * <br>
	 * author: 周震 date: 2015年6月29日 下午2:06:43
	 * 
	 * @param token
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param yonghuid
	 * @return
	 */
	@Override
	public List<GoodsForApp> getGoodsList(String types, int page_num,
			int page_size, String orderby, int hascomment, int comment_size,
			int yonghuid, int zhizuolx) {
		// TODO Auto-generated method stub
		int start = (page_num - 1) * page_size;
		String sql = "";
		String zhizuolxStr = "";
		if (zhizuolx != -1) {
			zhizuolxStr = " and ZhizuoLx=" + zhizuolx + " ";
		}
		// 时间
		sql = "SELECT sp.* FROM shangpin AS sp RIGHT JOIN loginshanghu AS sh ON sp.FabuzheID = sh.ID WHERE FabuzheLx = 'shanghu' and Leixing in(?) and Shuliang > 0 and IsXiajia !=1 "
				+ zhizuolxStr
				+ " ORDER BY now() - sp.RukuSj < 10000 DESC,IF ((now() - sp.RukuSj < 10000) = 1,sh.VIPDengji,sp.RukuSj) DESC,sp.RukuSj DESC limit "
				+ start + "," + page_size;
		List<GoodsForApp> list = jdbcTemplate.query(sql,
				new Object[] { types }, new BeanPropertyRowMapper<GoodsForApp>(
						GoodsForApp.class));
		for (GoodsForApp goods : list) {
			String sql1 = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getId() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			if (photoList.size() > 0) {
				GoodsPhoto gp = photoList.get(0);
				String url = gp.getUrl();
				goods.setUrl(url);
				/*
				 * if (gp.getIsGongkai() == 1) {// 公开 goods.setUrl(url); } else
				 * {// 不公开 goods.setUrl("暂不公开"); }
				 */

			}
			if (hascomment == 1) {
				List<Comment> commentList = getCommentList(goods.getId(), 1, 4,
						"shangpin");
				goods.setComment_list(commentList);
			}
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
		}
		return list;
	}

	@Override
	public List<HistoryShowForApp> getHistoryShowForApp(String types,
			int page_num, int page_size, String orderby, int hascomment,
			int comment_size, int yonghuid) {
		int start = (page_num - 1) * page_size;
		String sql = "select *,DATE_FORMAT(NOW(),'%j')-DATE_FORMAT(RukuSj,'%j') as days,DATE_FORMAT(NOW(),'%u')-DATE_FORMAT(RukuSj,'%u') as weeks,DATE_FORMAT(NOW(),'%m')-DATE_FORMAT(RukuSj,'%m') as months from shangpin where Leixing in ('shejixiu','chengyixiu') and FabuzheID="
				+ yonghuid
				+ " and IsMaiduan !=1 "
				+ " and IsXiajia=0 "
				+ " and FabuzheLx='shanghu' "
				+ " and DATE_FORMAT(RukuSj,'%Y')>(DATE_FORMAT(NOW(),'%Y')-1) order by RukuSj"
				+ " desc limit " + start + "," + page_size;
		List<HistoryShowForApp> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<HistoryShowForApp>(
						HistoryShowForApp.class));
		for (HistoryShowForApp goods : list) {

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

			String sql1 = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getId() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			if (photoList.size() > 0) {
				// String url = photoList.get(0).getUrl();
				// goods.setUrl(url);
				GoodsPhoto gp = photoList.get(0);
				String url = gp.getUrl();
				goods.setUrl(url);
				/*
				 * if (gp.getIsGongkai() == 1) {// 公开 goods.setUrl(url); } else
				 * {// 不公开 goods.setUrl("暂不公开"); }
				 */
			}
			if (hascomment == 1) {
				List<Comment> commentList = getCommentList(goods.getId(), 1, 4,
						"shangpin");
				goods.setComment_list(commentList);
			}
		}
		return list;
	}

	@Override
	public List<HistoryShowForApp> getHistoryShowForApp2(String types,
			int page_num, int page_size, String orderby, int hascomment,
			int comment_size, int yonghuid) {
		int start = (page_num - 1) * page_size;
		String sql = "select *,DATE_FORMAT(NOW(),'%j')-DATE_FORMAT(RukuSj,'%j') as days,DATE_FORMAT(NOW(),'%u')-DATE_FORMAT(RukuSj,'%u') as weeks,DATE_FORMAT(NOW(),'%m')-DATE_FORMAT(RukuSj,'%m') as months from shangpin where Leixing in ('shejixiu','chengyixiu') and FabuzheID="
				+ yonghuid
				+ " and FabuzheLx='shanghu' "
				+ " and DATE_FORMAT(RukuSj,'%Y')>(DATE_FORMAT(NOW(),'%Y')-1) order by RukuSj"
				+ " desc limit " + start + "," + page_size;
		List<HistoryShowForApp> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<HistoryShowForApp>(
						HistoryShowForApp.class));
		for (HistoryShowForApp goods : list) {

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

			String sql1 = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getId() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			if (photoList.size() > 0) {
				// String url = photoList.get(0).getUrl();
				// goods.setUrl(url);
				GoodsPhoto gp = photoList.get(0);
				String url = gp.getUrl();
				// if (gp.getIsGongkai() == 1)
				// {// 公开
				// goods.setUrl(url);
				// }
				// else
				// {// 不公开
				// goods.setUrl("暂不公开");
				// }
				goods.setUrl(url);
			}
			if (hascomment == 1) {
				List<Comment> commentList = getCommentList(goods.getId(), 1, 4,
						"shangpin");
				goods.setComment_list(commentList);
			}
		}
		return list;
	}

	/**
	 * 获取商户历史作品
	 * 
	 * @param types
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param hascomment
	 * @param comment_size
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Override
	public List<GoodsForApp> getDesignerShowList(String types, int page_num,
			int page_size, String orderby, int hascomment, int comment_size,
			int id) {
		int start = (page_num - 1) * page_size;
		String sql = "";
		// 时间
		if ("sj".equals(orderby)) {
			sql = "select * from shangpin where Leixing=? order by RukuSj"
					+ " desc limit " + start + "," + page_size;
			// 价格
		} else {
			sql = "select * from shangpin where Leixing=? order by Jiage"
					+ " desc limit " + start + "," + page_size;
		}
		List<GoodsForApp> list = jdbcTemplate.query(sql,
				new Object[] { types }, new BeanPropertyRowMapper<GoodsForApp>(
						GoodsForApp.class));
		for (GoodsForApp goods : list) {
			String sql1 = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getId() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			if (photoList.size() > 0) {
				// String url = photoList.get(0).getUrl();
				// goods.setUrl(url);
				GoodsPhoto gp = photoList.get(0);
				String url = gp.getUrl();
				goods.setUrl(url);
				/*
				 * if (gp.getIsGongkai() == 1) {// 公开 goods.setUrl(url); } else
				 * {// 不公开 goods.setUrl("暂不公开"); }
				 */
			}
			if (hascomment == 1) {
				List<Comment> commentList = getCommentList(goods.getId(), 1, 4,
						"shangpin");
				goods.setComment_list(commentList);
			}

		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getGoodsList(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean postComment(String commenter_id, String to_commenter_id,
			String types, String content, String pinglunrenLx) {
		// TODO Auto-generated method stub
		String sql = "";
		sql = "insert into pinglun(PinglunRenID,PinglunDxID,Leixing,Neirong,RukuSj,PinglunrenLx) values(?,?,?,?,Now(),?)";
		int num = jdbcTemplate.update(sql, commenter_id, to_commenter_id,
				types, content, pinglunrenLx);
		if ("shangpin".equals(types)) {
			String sql1 = "UPDATE shangpin SET new_discuss_count=new_discuss_count+1 WHERE ID=?";
			jdbcTemplate.update(sql1, new Object[] { to_commenter_id });
		}
		return num > 0;
	}

	@Override
	public boolean postGoodsComment(String order_id, String shangpin_id,
			int pingfen, String content, String user_id, String utypes) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String sql = "insert into dingdanchanpinpingjia(Dingdanid,Chanpinid,Pingfen,Content,Userid,YonghuLx,RukuSj) values(?,?,?,?,?,?,Now())";
		int num = jdbcTemplate.update(sql, order_id, shangpin_id, pingfen,
				content, user_id, utypes);
		if (num > 0) {
			// 修改订单状态为已评价
			if (modifyOrderStatus(order_id, 256) > 0) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public List<Comment> getCommentList(String id, int page_num, int page_size,
			String types) {
		// TODO Auto-generated method stub
		int start = (page_num - 1) * page_size;
		String typeStr = "";
		if ("shangpin".equals(types)) {
			typeStr = " and Leixing='shangpin' ";
		} else {
			typeStr = " and Leixing='ren' ";
		}
		String sql = "select PinglunRenID as userId,Neirong as content,PinglunrenLx as userType ,RukuSj as createDate from pinglun where PinglunDxID=? "
				+ typeStr
				+ "order by RukuSj desc limit "
				+ start
				+ ","
				+ page_size;

		List<Comment> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<Comment>(Comment.class));
		for (Comment comment : list) {
			if (UserType.BUYER.getValue().equals(comment.getUserType())) {
				String sql1 = "select UserName as name,Dengji as lv,Jingyan as jingyan from loginkehu where ID=?";
				List<Buyer> buyerList = jdbcTemplate.query(sql1,
						new Object[] { comment.getUserId() },
						new BeanPropertyRowMapper<Buyer>(Buyer.class));
				if (buyerList != null && buyerList.size() > 0) {
					Buyer buyer = buyerList.get(0);
					comment.setName(buyer.getName());
					// comment.setLv(buyer.getLv());
					try {
						comment.setLv(getLvByJingYan(buyer.getJingyan()));
					} catch (Exception e) {
						// TODO: handle exception
						comment.setLv(0);
					}
				}

			} else {
				String sql2 = "select UserName as name,Dengji as lv,Jingyan as jingyan from loginshanghu where ID=?";
				List<Saler> buyerList = jdbcTemplate.query(sql2,
						new Object[] { comment.getUserId() },
						new BeanPropertyRowMapper<Saler>(Saler.class));
				if (buyerList != null && buyerList.size() > 0) {
					Saler saler = buyerList.get(0);
					comment.setName(saler.getName());
					try {
						comment.setLv(getLvByJingYan(saler.getJingyan()));
					} catch (Exception e) {
						// TODO: handle exception
						comment.setLv(0);
					}
				}
			}
		}
		return list;
	}

	@Override
	public int getGoodsRemainNum(String id) {
		// TODO Auto-generated method stub
		String sql = "select Shuliang from shangpin where ID=" + id;
		@SuppressWarnings("deprecation")
		int num = jdbcTemplate.queryForInt(sql);
		return num;
	}

	@Override
	public GoodsForApp getGoodsDetail(String id) {
		// TODO Auto-generated method stub
		String sql = "select * from shangpin where ID=?";
		List<GoodsForApp> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<GoodsForApp>(GoodsForApp.class));

		if (list.size() > 0) {
			GoodsForApp goods = list.get(0);
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
			// 按照10级计算基础价格
			try {
				goods.setBaseJiage(getBaseJiage(goods));
				goods.setX(getNanduxishu(goods.getZhizuoLx()));
			} catch (Exception e) {
				// 抛异常，直接返回原价
				goods.setBaseJiage(goods.getJiage());
				goods.setX(1);
			}
			return list.size() > 0 ? list.get(0) : null;
		} else {
			return null;
		}

	}

	@Override
	public List<GoodsPhoto> getGoodsPhotoList2(String id) {
		// TODO Auto-generated method stub
		String sql = "select Url as url,ID as id,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
		List<GoodsPhoto> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
		// int size = list.size();
		// String urlList[] = new String[size];
		// for (int i = 0; i < size; i++)
		// {
		// urlList[i] = list.get(i).getUrl();
		// }
		return list;
	}

	@Override
	public String[] getGoodsPhotoList(String id) {
		// TODO Auto-generated method stub
		String sql = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
		List<GoodsPhoto> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
		int size = list.size();
		String urlList[] = new String[size];
		for (int i = 0; i < size; i++) {
			urlList[i] = list.get(i).getUrl();
		}
		return urlList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int submitOrder(OrderForApp order) {
		int num1 = 0;
		int goodsNum = 0;
		boolean flag = false;
		int id = 0;
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		String sql0 = "select Shuliang from shangpin where id=?";
		if (order == null || null == order.getShangpinID()) {
			return 0;
		}
		goodsNum = jdbcTemplate.queryForInt(sql0, order.getShangpinID());
		// 生成订单号
		String Bianhao = DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSSSSS) + "-"
				+ Utils.random6();
		order.setBianhao(Bianhao);
		// 设置买家用户名
		String buyerUsernameSql = "";
		if (UserType.BUYER.getValue().equals(order.getMaijiaLx())) {
			buyerUsernameSql = "select UserName as name from LoginKehu where ID=?";
			List<Buyer> buyerList = jdbcTemplate.query(buyerUsernameSql,
					new Object[] { order.getMaijiaID() },
					new BeanPropertyRowMapper<Buyer>(Buyer.class));
			if (buyerList.size() > 0) {
				Buyer buyer = buyerList.get(0);
				order.setBuyerName(buyer.getName());
			}
		} else {
			buyerUsernameSql = "select UserName as name from LoginShanghu where ID=?";
			List<Saler> salerList = jdbcTemplate.query(buyerUsernameSql,
					new Object[] { order.getMaijiaID() },
					new BeanPropertyRowMapper<Saler>(Saler.class));
			if (salerList.size() > 0) {
				Saler saler = salerList.get(0);
				order.setBuyerName(saler.getName());
			}
		}
		// 设置卖家用户名
		String salerUsernameSql = "select UserName as name from LoginShanghu where ID=?";
		List<Saler> salerList = jdbcTemplate.query(salerUsernameSql,
				new Object[] { order.getMmaijiaID() },
				new BeanPropertyRowMapper<Saler>(Saler.class));
		if (salerList.size() > 0) {
			Saler saler = salerList.get(0);
			order.setSalerName(saler.getName());
		}
		// 判断商品个数
		if (order.getIsQiangdan() == 0) {// 非抢单需要判断数量
			if (goodsNum > 0) {
				String sql = "insert into dingdan(ShangpinLx,danjia,Youhuijia,Yuanjia,Zongjia,Bianhao,ShangpinID,Shuliang,MaijiaID,MmaijiaID,Chima,Shengao,Tizhong,Yaowei,Xiongwei,Tunwei,Miaoshu,RukuSj,Zhuangtai,maijiaLx,MmaijiaLx,Shouhuoren,Shouhuodianhua,Shouhuodizhi,Youbian,IsQiangdan,IsYuanSjs,JiagongSj,KuaidiFs,buyer_username,saler_username) "
						+ "values(:shangpinlx,:danjia,:youhuijia,:yuanjia,:zongjia,:Bianhao,:ShangpinID,:Shuliang,:MaijiaID,:MmaijiaID,:Chima,:Shengao,:Tizhong,:Yaowei,:Xiongwei,:Tunwei,:Miaoshu,Now(),1,:maijiaLx,:MmaijiaLx,:Shouhuoren,:Shouhuodianhua,:Shouhuodizhi,:Youbian,:IsQiangdan,:IsYuanSjs,:JiagongSj,:KuaidiFs,:buyerName,:salerName)";

				num1 = namedJdbcTemplate.update(sql,
						new BeanPropertySqlParameterSource(order),
						generatedKeyHolder);
				flag = num1 > 0;
				// 订单失效时间为30min
			}
		} else {
			// 抢单不需要判断数量
			String status = "0";
			// 插入当前订单
			String sql = "insert into dingdan(ShangpinLx,danjia,Youhuijia,Yuanjia,Zongjia,Bianhao,ShangpinID,Shuliang,MaijiaID,MmaijiaID,Chima,Shengao,Tizhong,Yaowei,Xiongwei,Tunwei,Miaoshu,RukuSj,Zhuangtai,maijiaLx,MmaijiaLx,Shouhuoren,Shouhuodianhua,Shouhuodizhi,Youbian,IsQiangdan,IsYuanSjs,JiagongSj,KuaidiFs,buyer_username,saler_username) "
					+ "values(:shangpinlx,:danjia,:youhuijia,:yuanjia,:zongjia,:Bianhao,:ShangpinID,:Shuliang,:MaijiaID,:MmaijiaID,:Chima,:Shengao,:Tizhong,:Yaowei,:Xiongwei,:Tunwei,:Miaoshu,Now(),"
					+ status
					+ ",:maijiaLx,:MmaijiaLx,:Shouhuoren,:Shouhuodianhua,:Shouhuodizhi,:Youbian,:IsQiangdan,:IsYuanSjs,:JiagongSj,:KuaidiFs,:buyerName,:salerName)";
			num1 = namedJdbcTemplate.update(sql,
					new BeanPropertySqlParameterSource(order),
					generatedKeyHolder);
			flag = num1 > 0;
			// 判断当前用户是否是等级最高且时间最短,是的话修改
			String mmaijiaID = getNBBuyerId(order);
			if (mmaijiaID != null && mmaijiaID.length() > 0) {
				modifyOrderStatusBySpID(order.getShangpinID(), 1, mmaijiaID);
			}
		}
		if (flag) {
			id = generatedKeyHolder.getKey().intValue();
		} else {
			id = 0;
		}
		return id;
	}

	/**
	 * 获取最牛逼的设计师ID（vip等级最高，等级最高，加工时间最短）
	 * 
	 * @return
	 */
	@Override
	public String getNBBuyerId(OrderForApp order) {

		String id = null;
		String sql = "SELECT a.ID,a.VIPDengji,b.JiagongSj,b.ID as dingdanID FROM loginshanghu AS a RIGHT JOIN dingdan AS b ON a.ID = b.MmaijiaID WHERE"
				+ " b.ShangpinID = "
				+ order.getShangpinID()
				+ " AND b.IsQiangdan = 1"
				+ " AND b.Zhuangtai in(0,1)"
				+ " AND (a.unlock_time < NOW() OR a.unlock_time IS NULL) "
				+ " ORDER BY a.VIPDengji DESC,a.Dengji DESC,b.JiagongSj ASC LIMIT 1";
		try {
			Map<String, Object> list = jdbcTemplate.queryForMap(sql);
			id = list.get("ID").toString();
			return id;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 修改最牛逼的订单状态为1（vip等级最高，等级最高，加工时间最短）
	 * 
	 * @return
	 */
	@Override
	public boolean updateNBBuyerOrder(OrderForApp order) {
		String dingdanID = null;
		String sql = "SELECT a.ID,a.VIPDengji,b.JiagongSj,b.ID as dingdanID FROM loginshanghu AS a RIGHT JOIN dingdan AS b ON a.ID = b.MmaijiaID WHERE"
				+ " b.ShangpinID = "
				+ order.getShangpinID()
				+ " AND b.IsQiangdan = 1"
				+ " AND b.Zhuangtai in(0,1)"
				+ " ORDER BY a.VIPDengji DESC,a.Dengji DESC,b.JiagongSj ASC LIMIT 1";
		try {
			Map<String, Object> list = jdbcTemplate.queryForMap(sql);
			dingdanID = list.get("dingdanID").toString();
			if (dingdanID != null) {
				// 修改此订单状态
				return modifyOrderStatus(dingdanID, 1) > 0;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean isBuyerExit(String id) {
		String sql = "select count(*) from loginkehu where LoginID=?";
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql, id);
		return count > 0 ? true : false;
	}

	/**
	 * 修改买家信息<br>
	 * 修改个人信息时候使用
	 * 
	 * @param buyer
	 * @return
	 */
	@Override
	public boolean updateBuyer(Buyer buyer) {
		// 根据买家登录id修改买家信息
		String sql = "update loginkehu set LoginID=:loginId,UserName=:name,Xingbie=:xingbie "
				+ "where LoginID=:loginId";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int num = this.namedJdbcTemplate.update(sql,
				new BeanPropertySqlParameterSource(buyer), generatedKeyHolder);
		// 更新买家尺寸列表
		return num > 0 ? true : false;
	}

	/**
	 * 增加买家信息 <br>
	 * 首次注册时候使用
	 * 
	 * @param buyer
	 * @return
	 */
	@Override
	public int addBuyer(Buyer buyer) {
		int id = -1;
		// 插入新买家信息
		String sql = "insert into loginkehu(LoginID,UserName,Xingbie,create_time) "
				+ "values(:loginId,:name,:xingbie,NOW())";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int num = this.namedJdbcTemplate.update(sql,
				new BeanPropertySqlParameterSource(buyer), generatedKeyHolder);

		if (num > 0) {
			try {
				id = generatedKeyHolder.getKey().intValue();
				// System.out.println("generatedKeyHolder.getKey().intValue():"+generatedKeyHolder.getKey().intValue());
				StandardSize standardSize = getStandardSize(buyer.getXingbie(),
						buyer.getSize_list().get(0).getNormal_size());
				// 增加用户尺寸
				String sql2 = "insert into yonghuchima(LoginID,Name,Chima,Shengao,Yaowei,Tunwei,Xiongwei,shoulder_breadth) "
						+ "values('"
						+ buyer.getLoginId()
						+ "','"
						+ "尺码一"
						+ "','"
						+ buyer.getSize_list().get(0).getNormal_size()
						+ "','"
						+ standardSize.getHeight()
						+ "','"
						+ standardSize.getWaist()
						+ "','"
						+ standardSize.getHip()
						+ "','"
						+ standardSize.getBust()
						+ "','"
						+ standardSize.getShoulder_breadth() + "')";
				this.jdbcTemplate.execute(sql2);
			} catch (Exception e) {
				id = -1;
			}

		}

		return id;
	}

	/**
	 * 增加买家尺寸
	 * 
	 * @param size
	 * @return
	 */
	@Override
	public int addMySize(UserSize size) {
		// 增加用户尺寸
		String sql = "insert into yonghuchima(LoginID,Name,Chima,Shengao,Tizhong,Yaowei,Tunwei,Xiongwei,Miaoshu) values(:loginId,:name,:normal_size,:height,:weight,:waist,:hip,:bust,:description)";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		this.namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(
				size), generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	@Override
	public List<UserSize> getMySizeList(String loginid) {
		String sql = "select ID as id,LoginID as loginId,Name as name,Chima as normal_size,Shengao as height,Tizhong as weight,Yaowei as waist,Tunwei as hip,Xiongwei as bust,Miaoshu as description from yonghuchima where LoginId=?";
		List<UserSize> list = jdbcTemplate.query(sql, new Object[] { loginid },
				new BeanPropertyRowMapper<UserSize>(UserSize.class));
		return list;
	}

	@Override
	public boolean isSalerExit(String loginId) {
		String sql = "select count(*) from loginshanghu where LoginID=?";
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql, loginId);
		return count > 0 ? true : false;
	}

	@Override
	public int addSaler(Saler saler) {
		int id = -1;
		// 插入新卖家信息
		String sql = "insert into loginshanghu(LoginID,UserName,Quyu,ZhifuZhanghu,create_time,bank_card_no,issuing_bank) "
				+ "values(:loginId,:name,:quyu,:zhifuzhanghu,now(),:bank_card_no,:issuing_bank)";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int num = this.namedJdbcTemplate.update(sql,
				new BeanPropertySqlParameterSource(saler), generatedKeyHolder);
		if (num > 0) {
			try {
				id = generatedKeyHolder.getKey().intValue();
			} catch (Exception e) {
				id = -1;
			}

		}
		return id;
	}

	/**
	 * 获取订单列表
	 * 
	 * @param types
	 * @param userid
	 * @param status
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @param shangpinid
	 * @return
	 */
	@Override
	public List<OrderForApp> getOrderList(String types, int userid,
			String status, int page_num, int page_size, int shangpinid,
			String leixing) {
		int start = (page_num - 1) * page_size;
		String sql = "";
		String whereStr = "";
		// 买家
		if ("mj".equals(types)) {

			if (status.equals("16")) {// 获取已买断
				whereStr = "a.MaijiaID = " + userid + " and a.MaijiaLx = '"
						+ leixing + "'";
			} else {
				whereStr = "a.MaijiaID = " + userid + " and a.MaijiaLx = '"
						+ leixing + "' and d.Leixing !='shejixiu'";
			}
		}
		// 卖家
		else {
			if (status.equals("1")) {// 获取已抢订单
				whereStr = "a.MmaijiaID = " + userid + " and a.MmaijiaLx = '"
						+ leixing + "' and isQiangDan = '1'";
			} else if (status.equals("16")) {// 获取已买断
				whereStr = "a.MmaijiaID = " + userid + " and a.MmaijiaLx = '"
						+ leixing + "'";
			} else {
				whereStr = "a.MmaijiaID = " + userid + " and a.MmaijiaLx = '"
						+ leixing + "' and d.Leixing !='shejixiu'";
			}

		}
		sql = "SELECT"
				+ " a.id AS id,a.buyer_username as mjname,a.saler_username as mmjname,a.danjia AS danjia,"
				+ "a.Bianhao AS bianhao," + "a.ShangpinID AS shangpinid,"
				+ "a.MaijiaID," + "a.MmaijiaID," + "a.MaijiaLx,"
				+ "a.MmaijiaLx," + "a.KuaidiFs as KuaidiFs,"
				+ "d.Leixing AS shangpinlx," + " d.Mingcheng AS mingcheng,"
				+ "a.Shuliang AS shuliang," + " a.zongjia AS zongjia"
				+ ",a.RukuSj " + ",a.Zhuangtai,a.IsQiangdan,a.Tuikuanzht "
				+ " FROM " + "dingdan AS a"
				+ " RIGHT JOIN shangpin AS d ON a.ShangpinID = d.ID "
				+ " WHERE " + whereStr + " and a.Zhuangtai in (" + status
				+ ")  order by a.RukuSj " + " desc limit " + start + ","
				+ page_size;
		List<OrderForApp> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<OrderForApp>(OrderForApp.class));
		for (OrderForApp goods : list) {
			String sql1 = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
			List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
					new Object[] { goods.getShangpinID() },
					new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
			String countSql = "SELECT COUNT(*) FROM loginshanghu AS a JOIN dingdan AS b ON b.MmaijiaID = a.ID "
					+ " WHERE b.ShangpinID = ? AND b.Zhuangtai IN (1, 0)";
			int count = jdbcTemplate.queryForObject(countSql,
					new Object[] { goods.getShangpinID() }, Integer.class);
			goods.setDesignerListCount(count);
			if (photoList.size() > 0) {
				// String url = photoList.get(0).getUrl();
				// goods.setUrl(url);
				GoodsPhoto gp = photoList.get(0);
				String url = gp.getUrl();
				goods.setUrl(url);
				/*
				 * if (gp.getIsGongkai() == 1) {// 公开 goods.setUrl(url); } else
				 * {// 不公开 goods.setUrl("暂不公开"); }
				 */

			}
			// 查询出买家姓名
			String sql2 = "";
			if (goods.getMaijiaLx().equals("kehu")) {
				sql2 = "select UserName as name from loginkehu where ID=?";
			} else {
				sql2 = "select UserName as name from loginshanghu where ID=?";

			}
			try {
				List<String> buyerNameList = jdbcTemplate.queryForList(sql2,
						String.class, new Object[] { goods.getMaijiaID() });
				if (buyerNameList != null && buyerNameList.size() > 0) {
					goods.setMjname(buyerNameList.get(0));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// 查询出卖家姓名
			String sql3 = "";
			if (goods.getMmaijiaLx().equals("kehu")) {
				sql3 = "select UserName as name from loginkehu where ID=?";
			} else {
				sql3 = "select UserName as name from loginshanghu where ID=?";

			}
			if (goods.getMmaijiaID() != null) {
				List<String> salerNameList = jdbcTemplate.queryForList(sql3,
						String.class, new Object[] { goods.getMmaijiaID() });
				if (salerNameList != null && salerNameList.size() > 0) {
					goods.setMmjname(salerNameList.get(0));
				}
			}

		}
		return list;

	}

	/**
	 * 
	 * @param id
	 *            商品id
	 * @param page_num
	 * @param page_size
	 * @param orderby
	 * @return
	 */
	@Override
	public List<BuyerForOrder> getBuyerList(String id, int page_num,
			int page_size, String orderby) {
		int start = (page_num - 1) * page_size;
		String sql = "SELECT a.ID AS userId,b.ID AS dingdanID, a.UserName as UserName,a.Dengji,b.Youhuijia,b.KuaidiFs,b.jiagongSj,b.Zhuangtai as zhuangTai,b.Tuikuanzht as tuikuanzht "
				+ " FROM loginshanghu AS a JOIN dingdan AS b ON b.MmaijiaID = a.ID WHERE b.ShangpinID = "
				+ id
				+ " AND b.Zhuangtai in (1,0) "
				+ " AND (a.unlock_time < NOW() OR a.unlock_time IS NULL) "
				+ " ORDER BY b.Zhuangtai DESC,a.Dengji DESC,b.jiagongSj ASC,b.KuaidiFs ASC,b.RukuSj ASC "
				+ " limit " + start + "," + page_size;
		List<BuyerForOrder> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BuyerForOrder>(BuyerForOrder.class));

		return list;
	}

	/**
	 * 获取订单详情
	 * 
	 * @param id
	 *            订单ID
	 * @return
	 */
	@Override
	public OrderForApp getOrderInfo(String id) {
		String sql = "SELECT"
				+ " a.Tunwei AS tunwei,a.Shengao AS shengao,a.Tizhong AS tizhong,a.Yaowei AS yaowei,a.Xiongwei AS xiongwei,a.Chima AS chima,a.Miaoshu AS miaoshu,"
				+ " a.id AS id,a.IsQiangdan AS IsQiangdan,a.yuanjia AS danjia,a.Shouhuoren AS shouhuoren,a.Youbian AS youbian,a.Shouhuodianhua AS shouhuodianhua,"
				+ " a.Shouhuodizhi AS shouhuodizhi,a.JiagongSj as JiagongSj,"
				+ " a.Bianhao AS bianhao," + "a.ShangpinID AS shangpinid,"
				+ "a.MaijiaID," + "a.MmaijiaID," + " a.MaijiaLx,"
				+ "a.MmaijiaLx," + "d.Leixing AS shangpinlx,"
				+ " d.Mingcheng AS mingcheng," + " a.danjia AS danjia,"
				+ "a.Shuliang AS shuliang," + " a.Youhuijia AS youhuijia,"
				+ " a.zongjia AS zongjia," + " a.Zhuangtai AS zhuangtai, "
				+ " a.RukuSj AS RukuSj, " + " a.KuaidiFs AS KuaidiFs, "
				+ " a.Shoukuanma AS Shoukuanma, "
				+ " a.payCode AS payCode,a.FahuoSj as FahuoSj, "
				+ " a.FukuanSj AS FukuanSj, d.ZhizuoLx as ZhizuoLx" + " FROM "
				+ "dingdan AS a "
				+ " RIGHT JOIN shangpin AS d ON a.ShangpinID = d.ID"
				+ " WHERE " + "a.ID = " + id;
		OrderForApp mOrderForApp = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<OrderForApp>(OrderForApp.class));
		// 查询出商品图片
		String sql1 = "select Url as url,IsGongkai as isGongkai from shangpintupian where ShangpinID=?";
		List<GoodsPhoto> photoList = jdbcTemplate.query(sql1,
				new Object[] { mOrderForApp.getShangpinID() },
				new BeanPropertyRowMapper<GoodsPhoto>(GoodsPhoto.class));
		if (photoList.size() > 0) {
			// String url = photoList.get(0).getUrl();
			// mOrderForApp.setUrl(url);
			GoodsPhoto gp = photoList.get(0);
			String url = gp.getUrl();
			mOrderForApp.setUrl(url);
			/*
			 * if (gp.getIsGongkai() == 1) {// 公开 mOrderForApp.setUrl(url); }
			 * else {// 不公开 mOrderForApp.setUrl("暂不公开"); }
			 */
		}
		// 查询出买家姓名
		String sql2 = "";
		if (UserType.BUYER.getValue().equals(mOrderForApp.getMaijiaLx())) {
			sql2 = "select UserName as name from loginkehu where ID=?";
		} else {
			sql2 = "select UserName as name from loginshanghu where ID=?";

		}
		List<String> list1 = jdbcTemplate.queryForList(sql2,
				new Object[] { mOrderForApp.getMaijiaID() }, String.class);
		mOrderForApp.setMjname(list1.size() > 0 ? list1.get(0) : "");
		mOrderForApp.setBuyerName(list1.size() > 0 ? list1.get(0) : "");

		// 查询出卖家姓名
		String sql3 = "";
		if (UserType.BUYER.getValue().equals(mOrderForApp.getMmaijiaLx())) {
			sql3 = "select UserName as name from loginkehu where ID=?";
		} else {
			sql3 = "select UserName as name from loginshanghu where ID=?";

		}
		List<String> list2 = jdbcTemplate.queryForList(sql3,
				new Object[] { mOrderForApp.getMmaijiaID() }, String.class);
		mOrderForApp.setMmjname(list2.size() > 0 ? list2.get(0) : "");
		mOrderForApp.setSalerName(list2.size() > 0 ? list2.get(0) : "");
		return mOrderForApp;
	}

	/**
	 * 修改订单状态
	 * 
	 * @param id
	 * @param status
	 *            0：待付款，未选择,1：待付款，已选择 ,2：已取消 4：已付款 8：待发货 32：已发货 64:已收货 128：待评价
	 *            256：已评价
	 * @return
	 */
	@Override
	public int modifyOrderStatus(String id, int status) {
		String sql = "update dingdan set Zhuangtai=? where id=?";
		// 如果是发货,增加发货时间
		if (status == 32) {
			sql = "update dingdan set Zhuangtai=?,FahuoSj=Now() where id=? ";
		}
		int num = jdbcTemplate.update(sql, status, id);
		return num;
	}

	/**
	 * 商户端收款操作
	 * 
	 * @param id
	 * @param status
	 * @param shoukuanma
	 * @return
	 */
	@Override
	public int modifyOrderStatus(String id, int status, String shoukuanma) {
		int num = 0;
		OrderForApp order = getOrderInfo(id);
		if ("32".equals(order.getZhuangtai())) {
			String sql = "update dingdan set Zhuangtai=? where id=? and Shoukuanma=?";
			num = jdbcTemplate.update(sql, status, id, shoukuanma);
		}
		return num;
	}

	/**
	 * 修改订单状态以及快递单号和快递方式, 进行发货操作
	 * 
	 * @param id
	 * @param status
	 *            0：待付款，未选择,1：待付款，已选择 , 2：已取消 4：已付款 8：待发货 32：已发货 64:已收货 128：待评价
	 *            256：已评价
	 * @return
	 */
	@Override
	public int modifyOrderStatus(String id, int status, int kuaidiID,
			String kuaididanhao) {
		int num = 0;
		OrderForApp order = getOrderInfo(id);
		if ("8".equals(order.getZhuangtai())) {
			String sql = "update dingdan set Zhuangtai=?,KuaidiID=?,Kuaididanhao=?,FahuoSj=Now() where id=? ";
			num = jdbcTemplate.update(sql, status, kuaidiID, kuaididanhao, id);
		}
		return num;
	}

	/**
	 * 确认收货
	 * 
	 * @param id
	 * @param pay_id
	 *            付款码
	 * @return
	 */
	@Override
	public int makerSureToReceipt(String id, String pay_id) {
		OrderForApp order = getOrderInfo(id);
		int num = 0;
		if ("32".equals(order.getZhuangtai())
				|| "512".equals(order.getZhuangtai())) {
			String sql = "update dingdan set Zhuangtai=64 where id=? and shoukuanma = ?";
			num = jdbcTemplate.update(sql, id, pay_id);
		}
		return num;
	}

	/**
	 * 延时收货
	 * 
	 * @param id
	 * @param reason
	 * @return
	 */
	@Override
	public int delayToReceipt(String id, String reason) {
		String sql = "update dingdan set Zhuangtai=512,YanchiYy = ? where id=?";
		int num = jdbcTemplate.update(sql, reason, id);
		return num;
	}

	/**
	 * 催款
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int dunningOrder(String id, String CuiKuanYy) {
		String sql = "update dingdan set CuikuanCt=CuikuanCt+1,CuiKuanYy=? where id=?";
		int num = jdbcTemplate.update(sql, CuiKuanYy, id);
		return num;
	}

	/**
	 * 增加收货地址
	 * 
	 * @param address
	 * @return
	 */
	@Override
	public int addShouhuodizhi(Address address) {
		String sql = "insert into shouhuodizhi(YonghuID,YonghuLx,Shouhuoren,ShouhuoDizhi,Dianhua,Youbian,Shengshiqu) values(:yonghuid,:yonghulx,:shouhuoren,:shouhuodizhi,:dianhua,:youbian,:shengshiqu)";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		this.namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(
				address), generatedKeyHolder);
		return generatedKeyHolder.getKey().intValue();
	}

	/**
	 * 修改收货地址
	 * 
	 * @param address
	 * @return
	 */
	@Override
	public int modifyShouhuodizhi(Address address) {
		String sql = "update shouhuodizhi set Shouhuoren=:shouhuoren,ShouhuoDizhi=:shouhuodizhi,Dianhua=:dianhua,Youbian=:youbian,Shengshiqu=:shengshiqu where ID=:id";
		int num = namedJdbcTemplate.update(sql,
				new BeanPropertySqlParameterSource(address));
		return num;
	}

	/**
	 * 查询出广告
	 * 
	 * @param types
	 * @param point
	 * @return
	 */
	@Override
	public List<Ads> getAdsList(String types, String point) {
		// 查询出广告
		String sql1 = "SELECT * FROM laad WHERE Types =? AND Point =?";
		List<Ads> adsList = jdbcTemplate.query(sql1, new Object[] { types,
				point }, new BeanPropertyRowMapper<Ads>(Ads.class));
		return adsList;
	}

	/**
	 * 获取快递列表
	 * 
	 * @return
	 */
	@Override
	public List<Express> getExpressList() {
		// 快递列表
		String sql1 = "SELECT ID,Name FROM fenlei WHERE Types =?";
		List<Express> expressList = jdbcTemplate.query(sql1,
				new Object[] { "kuaidi" }, new BeanPropertyRowMapper<Express>(
						Express.class));
		return expressList;
	}

	/**
	 * 增加商品
	 * 
	 * @param goods
	 * @return
	 */
	@Override
	public int addGoods(GoodsForApp goods) {

		String sql = "insert into shangpin(Fabuzheid,Fabuzhelx,Leixing,ZhizuoLx,Mingcheng,Shuliang,Jiage,Danjia,Chima,IsGongKai,Miaoshu,YanseMc,YanseCode,ShangpinMs,Shengao,Tizhong,Yaowei,Tunwei,Xiongwei,RukuSj,YuanshejishiID) values(:Fabuzheid,:Fabuzhelx,:Leixing,:ZhizuoLx,:Mingcheng,:Shuliang,:Jiage,:Danjia,:Chima,:IsGongKai,:Miaoshu,:YanseMc,:YanseCode,:ShangpinMs,:Shengao,:Tizhong,:Yaowei,:Tunwei,:Xiongwei,Now(),:YuanshejishiID)";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		this.namedJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(
				goods), generatedKeyHolder);
		if (goods.getUrl_list().length > 0) {
			String[] url_list = goods.getUrl_list();
			int l = url_list.length;
			for (int i = 0; i < l; i++) {
				// 保存地址
				String sql2 = "insert into shangpintupian(ShangpinID,Url,IsGongkai,JubaoZt,JubaoCs,ShanchuZt,RukuSj,TupianMc) values(:shangpinId,:url,:isGongkai,:jubaoZt,:jubaoCs,:shanChuZt,now(),:tupianMc)";
				GoodsPhoto gp = new GoodsPhoto();
				int id = generatedKeyHolder.getKey().intValue();
				gp.setShangpinId("" + id);
				gp.setUrl(url_list[i]);
				int isgk = 1;
				if (null != goods.getIsGongKai()) {
					isgk = Integer.parseInt(goods.getIsGongKai());
				}
				gp.setIsGongkai(isgk);
				gp.setJubaoZt(0);
				gp.setJubaoCs(0);
				gp.setTupianMc("123.png");
				GeneratedKeyHolder generatedKeyHolder2 = new GeneratedKeyHolder();
				this.namedJdbcTemplate.update(sql2,
						new BeanPropertySqlParameterSource(gp),
						generatedKeyHolder2);

			}
		} else {

		}
		return generatedKeyHolder.getKey().intValue();
	}

	@Override
	public List<MouldForApp> getMouldList(String id, int page_num,
			int page_size, String orderby) {
		int start = (page_num - 1) * page_size;
		String sql = "";
		// 时间
		if ("sj".equals(orderby)) {
			sql = "SELECT ID,Name,Jiage,ZhizuoLxID,description FROM muban WHERE ZhizuoLxID = "
					+ id + " order by ID desc limit " + start + "," + page_size;

		} else {
			// 价格
			sql = "SELECT ID,Name,Jiage,ZhizuoLxID,description FROM muban WHERE ZhizuoLxID = "
					+ id
					+ " order by b.Jiage desc limit "
					+ start
					+ ","
					+ page_size;
		}
		List<MouldForApp> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<MouldForApp>(MouldForApp.class));
		for (MouldForApp goods : list) {
			String sql1 = "select * from mubantupian where MubanID=?";
			List<MuBanTupian> urllist = jdbcTemplate.query(sql1,
					new Object[] { goods.getID() },
					new BeanPropertyRowMapper<MuBanTupian>(MuBanTupian.class));
			if (urllist.size() > 0) {
				goods.setUrl(urllist.get(0));
				goods.setUrl_list(urllist);
			} else {

			}
		}
		return list;
	}

	/**
	 * 获取模板详情
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public MouldForApp getMouldDetail(String id) {
		String sql = "SELECT ID,Name,Jiage,ZhizuoLxID,description FROM muban WHERE ID=?";
		List<MouldForApp> list = jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<MouldForApp>(MouldForApp.class));
		MouldForApp mMouldForApp = list.size() > 0 ? list.get(0) : null;
		if (mMouldForApp != null) {
			String sql1 = "select * from mubantupian where MubanID=?";
			List<MuBanTupian> urllist = jdbcTemplate.query(sql1,
					new Object[] { id },
					new BeanPropertyRowMapper<MuBanTupian>(MuBanTupian.class));
			if (urllist != null) {
				mMouldForApp.setUrl_list(urllist);
			}
		}
		return mMouldForApp;
	}

	/**
	 * 获取抢单个数
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int getOrderNum(String id) {
		String sql = "select COUNT(*) as num from dingdan where ShangpinID=?";
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql, id);
		return count;
	}

	@Override
	public List<ColorForApp> getColors() {
		// TODO Auto-generated method stub
		String sql = "select ID as id,NAME as name,Miaoshu AS colorCode from fenlei WHERE Types='yanse';";
		List<ColorForApp> mColorList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ColorForApp>(ColorForApp.class));

		return mColorList;
	}

	@Override
	public boolean changeOrderStatusBySpID(String shangpinid, int i,
			String dingdanid) {
		// TODO Auto-generated method stub
		String sql = "update dingdan set Zhuangtai=? where ShangpinID=? and IsQiangdan='1' and ID !=?";
		int num = jdbcTemplate.update(sql, i, shangpinid, dingdanid);
		return num > 0;
	}

	@Override
	public boolean modifyOrderStatusBySpID(String shangpinid, int status,
			String salerId) {
		// TODO Auto-generated method stub
		String sql1 = "update dingdan set Zhuangtai=? where ShangpinID=? and IsQiangdan='1' and MmaijiaID=?";
		int num1 = jdbcTemplate.update(sql1, status, shangpinid, salerId);
		String sql2 = "update dingdan set Zhuangtai=0 where ShangpinID=? and IsQiangdan='1' and MmaijiaID!=?";
		int num2 = jdbcTemplate.update(sql2, shangpinid, salerId);
		return num1 > 0 && num2 > 0;
	}

	@Override
	public List<String> getMmJiaIDListBySpIDForCustomization(String id) {
		String sql = "select MmaijiaID from dingdan WHERE ShangpinID=? and Zhuangtai=2 and ShangpinLx='dingzhidan'";
		List<String> list = jdbcTemplate.queryForList(sql, new Object[] { id },
				String.class);
		return list;
	}

	@Override
	public int addShuliang(OrderForApp order) {
		int num = 0;
		String sql = "update shangpin set Shuliang=Shuliang+? where id=?";
		num = jdbcTemplate.update(sql, order.getShuliang(),
				order.getShangpinID());
		return num;

	}

	@Override
	public boolean praise(int id) {
		String sql = "update shangpin set ZanCt=ZanCt+1 where id=?";
		int num = jdbcTemplate.update(sql, id);
		return num > 0;
	}

	@Override
	public boolean uploadPhoto(String userId, String type, String url,
			String fileName) {
		UserAvatar avatar = new UserAvatar();
		avatar.setDelete_status(0);
		avatar.setUserId(userId);
		avatar.setReport_count(0);
		avatar.setReport_status(0);
		avatar.setTupianmc(fileName);
		avatar.setUrl(url);
		avatar.setUserType(type);
		String sql0 = "select count(*) from yonghutouxiang WHERE YonghuID = ? and YonghuLx = ?";
		int count = jdbcTemplate.queryForObject(sql0, Integer.class, userId,
				type);
		String sql1 = "";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int id = 0;
		if (count > 0) {// 存在更新
			sql1 = "UPDATE yonghutouxiang SET Url = '" + url
					+ "' WHERE YonghuID = '" + userId + "' and YonghuLx = '"
					+ type + "'";
			int num2 = jdbcTemplate.update(sql1);
			return num2 > 0;
		} else {// 不存在插入
			sql1 = "insert into yonghutouxiang(YonghuID,Url,JubaoZt,JubaoCs,ShanchuZt,RukuSj,TupianMc,YonghuLx) "
					+ "values(:userId,:url,:report_status,:report_count,:delete_status,Now(),:tupianmc,:userType)";

			this.namedJdbcTemplate.update(sql1,
					new BeanPropertySqlParameterSource(avatar),
					generatedKeyHolder);
			id = generatedKeyHolder.getKey().intValue();
			if (id > 0) {
				String sql2 = "";
				int num = 0;
				if (UserType.BUYER.getValue().equals(type)) {
					sql2 = "update loginkehu set TouxiangID=? where ID=?";
					num = jdbcTemplate.update(sql2, id, userId);
				} else {
					sql2 = "update loginshanghu set TouxiangID=? where ID=?";
					num = jdbcTemplate.update(sql2, id, userId);
				}
				return num > 0;
			} else {
				return false;
			}
		}

	}

	@Override
	public boolean updateBuyerInfo(Buyer buyer) {
		if (buyer.getName() != null) {
			String sql = "update loginkehu set UserName=:name,Quyu=:quyu,weiboName=:weibo_name"
					+ ",weixinName=:weixin_name,NameCs=NameCs+1 where LoginID=:loginId";
			int num = namedJdbcTemplate.update(sql,
					new BeanPropertySqlParameterSource(buyer));
			return num > 0;
		} else {
			String sql = "update loginkehu set Quyu=:quyu,weiboName=:weibo_name"
					+ ",weixinName=:weixin_name where LoginID=:loginId";
			int num = namedJdbcTemplate.update(sql,
					new BeanPropertySqlParameterSource(buyer));
			return num > 0;
		}

	}

	@Override
	public boolean updateSalerInfo(Saler saler) {
		if (saler.getName() != null) {
			String sql = "update loginshanghu set UserName=:name,Quyu=:quyu,weiboName=:weibo_name"
					+ ",weixinName=:weixin_name,ZhifuZhanghu=:zhifuzhanghu,bank_card_no=:bank_card_no,issuing_bank=:issuing_bank,NameCs=NameCs+1 where LoginID=:loginId";
			int num = namedJdbcTemplate.update(sql,
					new BeanPropertySqlParameterSource(saler));
			return num > 0;
		} else {
			String sql = "update loginshanghu set Quyu=:quyu,weiboName=:weibo_name"
					+ ",weixinName=:weixin_name,ZhifuZhanghu=:zhifuzhanghu,bank_card_no=:bank_card_no,issuing_bank=:issuing_bank where LoginID=:loginId";
			int num = namedJdbcTemplate.update(sql,
					new BeanPropertySqlParameterSource(saler));
			return num > 0;
		}
	}

	@Override
	public boolean modifyUserSize(UserSize size) {
		// TODO Auto-generated method stub
		String sql = "update yonghuchima set Name=:name,Chima=:normal_size,Shengao=:height,Tizhong=:weight,Yaowei=:waist,Tunwei=:hip,Xiongwei=:bust,Miaoshu=:description where ID=:id";
		int num = namedJdbcTemplate.update(sql,
				new BeanPropertySqlParameterSource(size));
		return num > 0;
	}

	@Override
	public List<Address> getShouhuodizhi(String yonghuId, String yonghuLx) {
		String sql = "select ID as id,YonghuId as yonghuid,Shouhuoren AS shouhuoren,YonghuLx AS yonghulx,ShouhuoDizhi ,Dianhua as dianhua,Youbian as youbian ,Shengshiqu as shengshiqu from shouhuodizhi WHERE YonghuId=? and YonghuLx=?;";
		List<Address> mColorList = jdbcTemplate.query(sql, new Object[] {
				yonghuId, yonghuLx }, new BeanPropertyRowMapper<Address>(
				Address.class));
		// TODO Auto-generated method stub
		return mColorList;
	}

	@Override
	public boolean changeBuyer(String oldId, String newId) {
		// TODO Auto-generated method stub
		String sql = "update dingdan set Zhuangtai = 0  WHERE ID=" + oldId;
		int num = jdbcTemplate.update(sql);
		if (num > 0) {
			String sql2 = "update dingdan set Zhuangtai = 1  WHERE ID=" + newId;
			int num2 = jdbcTemplate.update(sql2);
			if (num2 > 0) {
				return true;
			} else {
				// 将已修改的状态改回来
				String sql3 = "update dingdan set Zhuangtai = 1  WHERE ID="
						+ oldId;
				jdbcTemplate.update(sql3);
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取抢单人员的待发货单个数
	 * 
	 * @param shangpinId
	 * @param maijiaID
	 * @param maijiaLx
	 * 
	 * @return
	 */
	@Override
	public int getNotSendOrderNum(String shangpinID, String maijiaID,
			String maijiaLx) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from dingdan where MaijiaID=? and maijiaLx=? and  Zhuangtai=8";
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql, maijiaID, maijiaLx);
		return count;
	}

	/**
	 * 判断是否已经抢单
	 * 
	 * @param shangpinID
	 * @param maijiaID
	 * @param maijiaLx
	 * @return
	 */
	@Override
	public boolean hasQiangDan(String shangpinID, String MmaijiaID,
			String MmaijiaLx) {
		String sql = "select count(*) from dingdan where ShangpinID=? and MmaijiaID=? and MmaijiaLx=? and Zhuangtai in (0,1)";
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql, shangpinID, MmaijiaID,
				MmaijiaLx);
		return count > 0;
	}

	/**
	 * 根据商品ID获取抢单个数
	 * 
	 * @param shangpinID
	 * @return
	 */
	@Override
	public int getHasQiangDanNumByShangpin(String shangpinID) {
		String sql = "select count(*) from dingdan where ShangpinID=? and Zhuangtai in (0,1)";
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql, shangpinID);
		return count;
	}

	/**
	 * 获取用户等级
	 * 
	 * @param maijiaID
	 *            卖家userId
	 * @param maijiaLx
	 *            卖家类型
	 * @return
	 */
	@Override
	public int getLVByIDandLX(String maijiaID, String maijiaLx) {
		// TODO Auto-generated method stub
		int lv = 0;
		if (maijiaLx.equals("shanghu")) {
			int sumJinagyan = 0;
			String sql2 = "SELECT Jingyan FROM zhizuojingyan WHERE YonghuID = ? AND ZhizuoLxID='0'";
			List<LVBean> list = jdbcTemplate.query(sql2,
					new Object[] { maijiaID },
					new BeanPropertyRowMapper<LVBean>(LVBean.class));
			// 细分经验总和
			if (list != null && list.size() > 0) {
				sumJinagyan = list.get(0).getJingyan();
			}
			System.out.println("ewJingyan:" + sumJinagyan);

			int jingyan = sumJinagyan;
			lv = getLvByJingYan(jingyan);
		}
		return lv;
	}

	@Override
	public int getLvByJingYan(int Jingyan) {
		// TODO Auto-generated method stub

		try {
			String sql = "SELECT jishu FROM zhizuolxdj WHERE ? >= JingyanMin and ? <= JingyanMax";
			int lv;
			lv = jdbcTemplate.queryForObject(sql, Integer.class, Jingyan,
					Jingyan);
			return lv;
		} catch (Exception e) {
			return 0;
		}

	}

	@Override
	public LVBean getLVBeanByJingYan(int Jingyan) {

		try {
			String sql = "SELECT jiagongfei as Y,jishu as dengji,JingyanMax FROM zhizuolxdj WHERE ? >= JingyanMin and ? <= JingyanMax";
			LVBean lv = jdbcTemplate.queryForObject(sql, new Object[] {
					Jingyan, Jingyan }, new BeanPropertyRowMapper<LVBean>(
					LVBean.class));
			return lv;
		} catch (Exception e) {
			return new LVBean();
		}

	}

	// @Override
	public int getYByJingYan(int Jingyan) {
		// TODO Auto-generated method stub

		try {
			String sql = "SELECT jiagongfei as Y FROM zhizuolxdj WHERE ? >= JingyanMin and ? <= JingyanMax";
			int Y;
			Y = jdbcTemplate.queryForObject(sql, Integer.class, Jingyan,
					Jingyan);
			return Y;
		} catch (Exception e) {
			return 0;
		}

	}

	@Override
	public boolean changeJingyanByPingfen(String order_id, int pingfen) {
		// TODO Auto-generated method stub
		int changeJingyan = 0;
		switch (pingfen) {
		case 1:
			changeJingyan = -10;
			break;
		case 2:
			changeJingyan = 0;
			break;
		case 3:
			changeJingyan = 2;
			break;
		case 4:
			changeJingyan = 5;
			break;
		case 5:
			changeJingyan = 10;
			break;
		}
		// 判断当日增加经验上限
		OrderForApp order = getOrderInfo(order_id);
		if (UserType.SALER.getValue().equals(order.getMmaijiaLx())) {// 卖家是商户,增加商户经验
																		// 修改商户等级
			String id = order.getMmaijiaID();
			String types = order.getMmaijiaLx();
			String ZhizuoLxID = order.getZhizuoLx();
			if (ZhizuoLxID.equals("0")) {
				return changeAllJingyan(changeJingyan, id, types, "0");
			} else {
				return changeAllJingyan(changeJingyan, id, types, ZhizuoLxID)
						&& changeAllJingyan(changeJingyan, id, types, "0");
			}
		} else {
			// 如果卖家是客户
			// 修改商户等级
			String id = order.getMmaijiaID();
			String types = order.getMmaijiaLx();
			String ZhizuoLxID = order.getZhizuoLx();
			if (ZhizuoLxID.equals("0")) {
				return changeAllJingyan(changeJingyan, id, types, "0");
			} else {
				return changeAllJingyan(changeJingyan, id, types, ZhizuoLxID)
						&& changeAllJingyan(changeJingyan, id, types, "0") ? true
						: false;
			}
		}
	}

	public boolean changeAllJingyan(int changeJingyan, String YonghuID,
			String YonghuLx, String ZhizuoLxID) {
		String sql = "UPDATE zhizuojingyan SET Jingyan = Jingyan+'"
				+ changeJingyan + "' WHERE YonghuID = '" + YonghuID
				+ "' AND YonghuLx = '" + YonghuLx + "' AND ZhizuoLxID = '"
				+ ZhizuoLxID + "'";
		int num = jdbcTemplate.update(sql);
		int num2 = 0;
		if (num <= 0) {
			String sql2 = "insert into zhizuojingyan(Jingyan,YonghuID,YonghuLx,ZhizuoLxID,Dengji) values('"
					+ changeJingyan
					+ "','"
					+ YonghuID
					+ "','"
					+ YonghuLx
					+ "','" + ZhizuoLxID + "','0')";
			num2 = jdbcTemplate.update(sql2);
		}
		int lv = getLVByIDandLX(YonghuID, YonghuLx);
		// 设置用户等级
		boolean flag = setLv(lv, YonghuID, YonghuLx);
		return (num + num2) > 0 && flag ? true : false;
	}

	@Override
	public boolean changeJingyanByDianZan(String good_id, int dianzan) {
		// 制作类型为0，时候表示额外经验
		int xishu_jangyan_dianzan = 1;
		int changeJingyan = dianzan * xishu_jangyan_dianzan;
		GoodsForApp goods = getGoodsDetail(good_id);
		if (UserType.SALER.getValue().equals(goods.getFabuzhelx())) {// 卖家是商户,增加商户经验
			String id = goods.getFabuzheid();
			String types = goods.getFabuzhelx();
			int ZhizuoLxID = goods.getZhizuoLx();
			if (ZhizuoLxID == 0) {
				return changeAllJingyan(changeJingyan, id, types, "0");
			} else {
				return changeAllJingyan(changeJingyan, id, types, ZhizuoLxID
						+ "")
						&& changeAllJingyan(changeJingyan, id, types, "0");
			}
		} else {
			// 如果卖家是客户,只增加自由定制经验
			String id = goods.getFabuzheid();
			String types = goods.getFabuzhelx();
			int ZhizuoLxID = goods.getZhizuoLx();
			if (ZhizuoLxID == 0) {// 如果制作类型为自由定制只增加自由定制经验
				return changeAllJingyan(changeJingyan, id, types, "0");
			} else {
				return changeAllJingyan(changeJingyan, id, types, ZhizuoLxID
						+ "")
						&& changeAllJingyan(changeJingyan, id, types, "0");
			}
		}
	}

	@Override
	public boolean xiugaiDizhi(OrderForApp order) {
		String sql = "UPDATE dingdan SET ShouhuoDianhua = :shouhuodianhua,ShouhuoDizhi=:shouhuodizhi,Shouhuoren=:shouhuoren,Youbian=:Youbian WHERE ID = :id";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int num = this.namedJdbcTemplate.update(sql,
				new BeanPropertySqlParameterSource(order), generatedKeyHolder);
		return num > 0;
	}

	@Override
	public boolean changeOrderPayCode(String dingdanid, String payCode) {
		boolean flag = false;
		try {
			OrderForApp order = getOrderInfo(dingdanid);
			if ("1".equals(order.getZhuangtai())
					|| "16".equals(order.getZhuangtai())
					|| "8".equals(order.getZhuangtai())) {
				int status = 8;
				if (order.getShangpinlx().equals("shejixiu")) {// 买断
					status = 16;
				}
				String sql = "UPDATE dingdan set payCode = ?,Zhuangtai = "
						+ status + " WHERE ID = ?";
				int num = jdbcTemplate.update(sql, payCode, dingdanid);
				flag = num > 0;
				return flag;
			} else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return flag;
		}

	}

	/**
	 * 更新订单状态
	 * 
	 * @return
	 */
	@Override
	public boolean updateOrderStatus() {

		// 修改非抢单, 把待付款订单的状态修改为已取消
		String sql = "UPDATE dingdan SET Zhuangtai='2' WHERE RukuSj < date_add(now(),interval -30 minute) and Zhuangtai in('0','1') and IsQiangdan='0'";
		int num = jdbcTemplate.update(sql);
		if (num > 0) {
			// 把失效订单移除
			Map<String, List<OrderForApp>> map = AppCtrl.orderMap;
			logger.debug("map.size()================" + map.size());
			OrderForApp order = null;
			for (String key : map.keySet()) {
				// 商品ID
				logger.debug("商品ID==================" + key);
				// 订单列表
				List<OrderForApp> l = map.get(key);
				logger.debug("订单列表个数==============" + l.size());
				// 判断订单是否失效
				order = getOrderForApp(l, key);
				// 时间戳
				logger.debug("时间戳==================" + order.getTime());

				long time = 0;
				if (order != null) {
					time = order.getTime();
					if (time > 0 && isShiXiao(time)) {// 没有失效不用处理

					} else {// 失效，删除失效订单
						List<OrderForApp> list = map.get(order.getShangpinID());
						list.remove(order);
					}
				}
			}
		}
		return num > 0;
	}

	/**
	 * 
	 * 判断是否失效 <功能详细描述>
	 * 
	 * @param hqtime
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean isShiXiao(long hqtime) {
		Long s = (System.currentTimeMillis() - hqtime) / (1000);
		System.out.println("s:" + s);
		return s < 1810; // 设置成30min多10秒，防止数据库修改失败而释放掉有效订单
	}

	/**
	 * 根据商品ID获取列表里的订单
	 * 
	 * @param l
	 * @param key
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public OrderForApp getOrderForApp(List<OrderForApp> l, String key) {
		OrderForApp order = null;
		String spID = "";
		for (int i = 0; i < l.size(); i++) {
			order = l.get(i);
			spID = order.getShangpinID();
			if (spID.equals(key)) {
				return order;
			} else {

			}
		}
		return order;

	}

	/**
	 * 举报
	 * 
	 * @param photo
	 * @return
	 */
	@Override
	public boolean jubao(PhotoBean photo) {
		/**
		 * 1.客户用户头像 2.商户用户头像 3.商品图片 4.模板图片
		 */
		int type = photo.getType();
		int id = photo.getId();
		String sql = "";
		switch (type) {
		case 1:// 1.客户用户头像
			sql = "UPDATE yonghutouxiang SET JubaoZt=1,JubaoCs=JubaoCs+1 WHERE ID="
					+ id;
			break;
		case 2:// 2.商户用户头像
			sql = "UPDATE yonghutouxiang SET JubaoZt=1,JubaoCs=JubaoCs+1 WHERE ID="
					+ id;
			break;
		case 3:// 3.商品图片
			sql = "UPDATE shangpintupian SET JubaoZt=1,JubaoCs=JubaoCs+1 WHERE ID="
					+ id;
			break;
		case 4:// 4.模板图片
			sql = "UPDATE mubantupian SET JubaoZt=1,JubaoCs=JubaoCs+1 WHERE ID="
					+ id;
			break;
		default:
			break;
		}
		int num = jdbcTemplate.update(sql);
		return num > 0;
	}

	@Override
	public boolean changeToMaiduan(String shangpinID) {
		// TODO Auto-generated method stub
		String sql = "UPDATE shangpin SET IsMaiduan=1 WHERE ID=" + shangpinID;
		int num = jdbcTemplate.update(sql);
		return num > 0;
	}

	@Override
	public boolean clearDiscussCount(int goodId) {
		// TODO Auto-generated method stub
		String sql = "UPDATE shangpin SET new_discuss_count='0' WHERE ID=?";
		int num = jdbcTemplate.update(sql, new Object[] { goodId });
		return num > 0;
	}

	@Override
	public int getNewDiscussCount(int designerId) {
		// TODO Auto-generated method stub
		String sql = "SELECT SUM(new_discuss_count) FROM shangpin WHERE FabuzheLx='shanghu' AND FabuzheID=? AND (Leixing='shejixiu' OR Leixing='chengyixiu')";
		Integer num = jdbcTemplate.queryForObject(sql,
				new Object[] { designerId }, Integer.class);
		return num == null ? 0 : num;
	}

	@Override
	public int getChangeCs(String loginId, String types) {
		String tablename = "loginkehu";
		if (UserType.BUYER.getValue().equals(types)) {
			tablename = "loginkehu";
		} else {
			tablename = "loginshanghu";

		}
		String sql = "SELECT NameCs FROM " + tablename + " WHERE LoginID=?";
		int num = jdbcTemplate.queryForObject(sql, new Object[] { loginId },
				Integer.class);
		return num;
	}

	@Override
	public void updateLoginTime(String userId, String userType) {
		// TODO Auto-generated method stub
		String sql = "";
		if (UserType.BUYER.getValue().equals(userType)) {
			sql = "UPDATE loginkehu SET last_login_time=NOW() WHERE ID=?";
			jdbcTemplate.update(sql, new Object[] { userId });
		} else {
			sql = "UPDATE loginshanghu SET last_login_time=NOW() WHERE ID=?";
			jdbcTemplate.update(sql, new Object[] { userId });
		}
	}

	@Override
	public boolean isFenghao(String id, String types) {
		String sql = "";
		int num = 0;
		if (UserType.BUYER.getValue().equals(types)) {
			sql = "SELECT COUNT(*) FROM loginkehu where ID = ? AND unlock_time>NOW()";
			num = jdbcTemplate.queryForObject(sql, new Object[] { id },
					Integer.class);
		} else {
			sql = "SELECT COUNT(*) FROM loginshanghu where ID = ? AND unlock_time>NOW()";
			num = jdbcTemplate.queryForObject(sql, new Object[] { id },
					Integer.class);
		}
		return num > 0;
	}

	@Override
	public String getSucMmJiaIDBySpIDForCustomization(String id) {
		// TODO Auto-generated method stub
		String sql = "select MmaijiaID from dingdan WHERE ShangpinID=? and Zhuangtai=8 and ShangpinLx='dingzhidan'";
		List<String> list = jdbcTemplate.queryForList(sql, new Object[] { id },
				String.class);
		return list.size() > 0 ? list.get(0) : "";
	}

	@Override
	public boolean setLv(int lv, String userId, String userType) {
		// TODO Auto-generated method stub
		String sql = "";
		int num = 0;
		if (UserType.BUYER.getValue().equals(userType)) {
			sql = "UPDATE loginkehu SET Dengji=? WHERE ID=?";
			num = jdbcTemplate.update(sql, new Object[] { lv, userId });
		} else {
			sql = "UPDATE loginshanghu SET Dengji=? WHERE ID=?";
			num = jdbcTemplate.update(sql, new Object[] { lv, userId });
		}

		return num > 0;
	}

	@Override
	public int getExp(String userId, String userType) {
		// TODO Auto-generated method stub
		String sql = "";
		int exp = 0;
		if (UserType.BUYER.getValue().equals(userType)) {
			sql = "SELECT Jingyan FROM zhizuojingyan WHERE YonghuID=? AND ZhizuoLxID='0' AND YonghuLx='kehu'";
			List<Integer> list = jdbcTemplate.queryForList(sql,
					new Object[] { userId }, Integer.class);
			if (list != null && list.size() > 0) {
				exp = list.get(0);
			}
		} else {
			sql = "SELECT Jingyan FROM zhizuojingyan WHERE YonghuID=? AND ZhizuoLxID='0' AND YonghuLx='shanghu'";
			List<Integer> list = jdbcTemplate.queryForList(sql,
					new Object[] { userId }, Integer.class);
			if (list != null && list.size() > 0) {
				exp = list.get(0);
			}
		}
		return exp;
	}

	/**
	 * 确认支付
	 * 
	 * @param id
	 * @param payCode
	 * @return
	 */
	@Override
	public int confirmPay(String id, String payCode, String zhuangtai) {
		// 生成收款码
		String shoukuanma = DateUtil.getDate(DateUtil.YYYY_MM_DD_HHMMSSSSS)
				+ "-" + Utils.random6();
		String sql = "update dingdan set Zhuangtai=?,shoukuanma=?,payCode=?,FukuanSj=Now() where id=?";
		int num = jdbcTemplate.update(sql, zhuangtai, shoukuanma, payCode, id);
		int num2 = 0;
		if (num > 0) {
			OrderForApp order = getOrderInfo(id);
			// 抢单，减少商品数量
			String sql2 = "update shangpin set Shuliang=Shuliang-? where id=?";
			num2 = jdbcTemplate.update(sql2, order.getShuliang(),
					order.getShangpinID());

		}
		return num2;
	}

	@Override
	public boolean deleteAddress(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM shouhuodizhi WHERE ID=?";
		int num = jdbcTemplate.update(sql, new Object[] { id });
		return num > 0;
	}

	@Override
	public StandardSize getStandardSize(String sex, String normalSize) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM t_size WHERE sex=? AND size=?";
		List<StandardSize> list = jdbcTemplate.query(sql, new Object[] { sex,
				normalSize }, new BeanPropertyRowMapper<StandardSize>(
				StandardSize.class));
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<String> getMmJiaIDListBySpIDForWorksheet(String id) {
		// TODO Auto-generated method stub
		String sql = "select MmaijiaID from dingdan WHERE ShangpinID=? and Zhuangtai=2 and ShangpinLx='jiagongdan'";
		List<String> list = jdbcTemplate.queryForList(sql, new Object[] { id },
				String.class);
		return list;
	}

	@Override
	public String getSucMmJiaIDBySpIDForWorksheet(String id) {
		// TODO Auto-generated method stub
		String sql = "select MmaijiaID from dingdan WHERE ShangpinID=? and Zhuangtai=8 and ShangpinLx='jiagongdan'";
		List<String> list = jdbcTemplate.queryForList(sql, new Object[] { id },
				String.class);
		return list.size() > 0 ? list.get(0) : "";
	}

	@Override
	public List<PoemMod> getPoemList(int id, int size) {
		// TODO Auto-generated method stub
		String sql = "select * from poem_t";
//		List<PoemMod> list = jdbcTemplate
//				.queryForList(sql, null, PoemMod.class);
		List<PoemMod> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<PoemMod>(PoemMod.class));
		return list;
	}
}
