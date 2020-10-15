package com.edu.portal.service.impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemParamItem;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.common.util.JsonUtils;
import com.edu.portal.bean.ItemCustomer;
import com.edu.portal.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Value("${CONTENT_BASE_URL}")
    private String CONTENT_BASE_URL;
    @Value("${ITEM_BASE_URL}")
    private String ITEM_BASE_URL;
    @Value("${ITEM_BASE_DESC}")
    private String ITEM_BASE_DESC;
    @Value("${ITEM_BASE_PARAM}")
    private String ITEM_BASE_PARAM;
    @Override
    public ItemCustomer getInfo(long itemId) {
        ItemCustomer itemCustomer = null;
        // 调用远程发布的服务 http://localhost:8081/rest/item/info/160067715230182
       String itemInfo =  HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_BASE_URL + itemId);
       // 把这个json格式的数据转换成TbItem对象
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemInfo, TbItem.class);
        if(shoppingResult.getStatus() == 200) {
            TbItem tbItem = (TbItem)shoppingResult.getData();
            if(null != tbItem) {
                itemCustomer = new ItemCustomer() ;
                BeanUtils.copyProperties(tbItem,itemCustomer);
                return itemCustomer ;
            }
        }
        return null;
    }

    @Override
    public String getDesc(long itemId) {
        TbItemDesc tbItemDesc = null ;
        String itemInfo =  HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_BASE_DESC + itemId);
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemInfo, TbItemDesc.class);
        if(shoppingResult.getStatus() == 200) {
            tbItemDesc = (TbItemDesc)shoppingResult.getData();
            if(null != tbItemDesc) {

                return tbItemDesc.getItemDesc() ;
            }
        }
        return null;
    }

    @Override
    public String getParam(long itemId) {
        StringBuilder sb = new StringBuilder();
        TbItemParamItem tbItemParamItem = null ;
        String itemInfo =  HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_BASE_PARAM + itemId);
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemInfo, TbItemParamItem.class);
        if(shoppingResult.getStatus() == 200) {
            tbItemParamItem = (TbItemParamItem)shoppingResult.getData();
            if(null != tbItemParamItem) {
                String paramData = tbItemParamItem.getParamData();
                // 把这个json字符串转换成数组
                List<Map> maps = JsonUtils.jsonToList(paramData, Map.class);
                sb.append("<table class=\"tm-tableAttr\">\n");
                sb.append("	<thead>\n");
                sb.append("		<tr>\n");
                sb.append("			<td colspan=\"2\">规格参数</td>\n");
                sb.append("		</tr>\n");
                sb.append("	</thead>\n");
                sb.append("	<tbody>\n");
                for(Map map:maps) {
                   String group =  (String)map.get("group");
                    sb.append("		<tr class=\"tm-tableAttrSub\">\n");
                    sb.append("			<th colspan=\"2\" data-spm-anchor-id=\"a220o.1000855.0.i0.18121eaahR0ynX\">"+group+"</th>\n");
                    sb.append("		</tr>\n");
                   List<Map> childrens = (List<Map>)map.get("params");
                   for(Map child:childrens) {
                       String key = (String)child.get("k");
                       String value = (String)child.get("v");
                       sb.append("		<tr>\n");
                       sb.append("			<th>"+key+"</th>\n");
                       sb.append("			<td data-spm-anchor-id=\"a220o.1000855.0.i2.18121eaahR0ynX\">&nbsp;"+value+"</td>\n");
                       sb.append("		</tr>\n");
                   }
                }
                sb.append("	</tbody>\n");
                sb.append("</table>");
                return sb.toString();
            }
        }
        return null;
    }
}
