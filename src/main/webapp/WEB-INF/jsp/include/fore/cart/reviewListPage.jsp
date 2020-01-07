<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<div class="reviewDiv">
    <c:forEach items="${ois}" var="oi">
        <div class="reviewProductInfoDiv">
            <div class="reviewProductInfoImg">
                <img width="300" height="300" src="img/productSingle/${oi.product.firstProductImage.id}.jpg">
            </div>
            <div class="reviewProductInfoRightDiv">
                <div class="reviewProductInfoRightText">
                        ${oi.product.name}
                </div>
                <table class="reviewProductInfoTable">
                    <tr>
                        <td width="75px">价格:</td>
                        <td><span class="reviewProductInfoTablePrice">￥
    <fmt:formatNumber type="number" value="${oi.product.originalPrice}" maxFractionDigits="2"/></span> 元
                        </td>
                    </tr>
                    <tr>
                        <td>配送</td>
                        <td>快递: 0.00</td>
                    </tr>
                    <tr>
                        <td>月销量:</td>
                        <td><span class="reviewProductInfoTableSellNumber">${oi.product.saleCount}</span> 件</td>
                    </tr>
                </table>

                <div class="reviewProductInfoRightBelowDiv">
                    <span class="reviewProductInfoRightBelowImg"><img src="/img/site/reviewLight.png"/></span>
                    <span class="reviewProductInfoRightBelowText">现在查看的是 您所购买商品的信息
    于<fmt:formatDate value="${o.createDate}" pattern="yyyy年MM月dd"/>下单购买了此商品 </span>
                    <c:if test="${oi.status == 0}">
                        <a href="/foreReview?id=${oi.id}">去评价</a>
                    </c:if>
                    <c:if test="${oi.status == 1}">
                        <a href="/foreReview?id=${oi.id}">查看评价</a>
                    </c:if>
                </div>
            </div>

            <div style="clear:both"></div>
        </div>
    </c:forEach>
</div>