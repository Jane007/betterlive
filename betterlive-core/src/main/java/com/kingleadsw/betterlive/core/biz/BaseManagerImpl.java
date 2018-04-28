package com.kingleadsw.betterlive.core.biz;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.core.dto.BasePO;
import com.kingleadsw.betterlive.core.dto.BaseVO;
import com.kingleadsw.betterlive.core.dto.GenericObjectTranslator;
import com.kingleadsw.betterlive.core.dto.ObjectTranslator;
import com.kingleadsw.betterlive.core.exception.ConvertException;
import com.kingleadsw.betterlive.core.page.PageData;
import com.kingleadsw.betterlive.core.service.BaseService;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Administrator on 2016-08-08.
 */
public abstract class BaseManagerImpl<VO extends BaseVO,PO extends BasePO> implements BaseManager<VO,PO>{
	
    private static Logger LOGGER = Logger.getLogger(BaseManagerImpl.class);

    private BaseService<PO> baseService;

    protected ObjectTranslator<PO, VO> po2voer = new GenericObjectTranslator<PO, VO>();

    protected ObjectTranslator<VO, PO> vo2poer = new GenericObjectTranslator<VO, PO>();


    protected ObjectTranslator generator = new GenericObjectTranslator();
    
    public ObjectTranslator<PO, VO> getPo2voer () {
        return po2voer;
    }

    public ObjectTranslator<VO, PO> getVo2poer () {
        return vo2poer;
    }
    /**
     * 获取服务接口，此方法建议在子类中重写
     * @return
     */
    protected abstract BaseService<PO> getService();

    /**
     * 实例化VO对象，相当于new VO()
     *
     * @return
     */
    protected VO createVo(){
        try {
            return getVoClass().newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("实例vo对象出错", e);
            throw new ConvertException("不能实例化'" + getVoClass().getName() + "'", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("实例vo对象出错", e);
            throw new ConvertException("不能实例化'" + getVoClass().getName() + "'", e);
        }
    }

    /**
     * 实例化PO对象，相当于new PO()
     *
     * @return
     */
    protected PO createPo(){
        try {
            return getPoClass().newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("实例po对象出错", e);
            throw new ConvertException("不能实例化'" + getPoClass().getName() + "'", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("实例po对象出错", e);
            throw new ConvertException("不能实例化'" + getPoClass().getName() + "'", e);
        }
    }

    public Class<PO> getPoClass() {
        return (Class<PO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public Class<VO> getVoClass() {
        return (Class<VO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<VO> queryListPage(PageData pd) {
        return po2voer.transfer(getVoClass(),getService().queryListPage(pd));
    }

    @Override
    public List<VO> queryList(PageData pd) {
        return po2voer.transfer(getVoClass(),getService().queryList(pd));
    }

    @Override
    public VO queryOne(PageData pd) {
        return po2voer.transfer(createVo(),getService().queryOne(pd));
    }

    @Override
    public VO selectByPrimaryKey(Integer id) {
        return po2voer.transfer(createVo(),getService().selectByPrimaryKey(id));
    }

    @Override
    public int delete(PageData pd) {
        return getService().delete(pd);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getService().deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(VO pd) {
        return getService().updateByPrimaryKey(vo2poer.transfer(createPo(),pd));
    }

    @Override
    public int updateByPrimaryKeySelective(VO record) {
        return getService().updateByPrimaryKeySelective(vo2poer.transfer(createPo(), record));
    }
    
    /**
     * 按条件修改
     * @param record
     * @return
     */
    @Override
    public int updatePageData(PageData pd) {
    	return getService().updatePageData(pd);
    }
    
    @Override
    public int insertSelective(VO record){
    	PO po = createPo();
	    int i = getService().insertSelective(vo2poer.transfer(po,record));
	    po2voer.transfer(record,po);
    	return i;
    }

    @Override
    public int insertPageData(PageData pd) {
        return getService().insertPageData(pd);
    }

    @Override
    public int insert(VO record) {
        PO po = createPo();
        int i = getService().insert(vo2poer.transfer(po,record));
        po2voer.transfer(record,po);
        return i;
    }
}
