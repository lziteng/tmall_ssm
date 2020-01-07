<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<div class="reviewDiv">

    <script>
        $(function () {
            $("#reviewOne").submit(function () {
                return checkEmpty("content", "评价信息");
            })
        })
    </script>

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
                    <a>待评价</a>
                </c:if>
                <c:if test="${oi.status == 1}">
                    <a>已完成评价</a>
                </c:if>
            </div>
        </div>

        <div style="clear:both"></div>
    </div>

    <div class="reviewStasticsDiv">
        <div class="reviewStasticsLeft">
            <div class="reviewStasticsLeftTop"></div>
            <div class="reviewStasticsLeftContent">累计评价 <span
                    class="reviewStasticsNumber"> ${oi.product.reviewCount}</span>
            </div>
            <div class="reviewStasticsLeftFoot"></div>
        </div>
        <div class="reviewStasticsRight">
            <div class="reviewStasticsRightEmpty"></div>
            <div class="reviewStasticsFoot"></div>
        </div>
    </div>

    <c:if test="${oi.status == 1}">
        <div class="reviewDivlistReviews">
            <div class="reviewDivlistReviewsEach">
                <div class="reviewDate"><fmt:formatDate value="${review.createDate}" pattern="yyyy-MM-dd"/></div>
                <div class="reviewContent">${review.content}</div>
                <div class="reviewUserInfo pull-right">${review.user.anonymousName}<span
                        class="reviewUserInfoAnonymous">(匿名)</span>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${oi.status == 0}">
        <div class="makeReviewDiv">
            <form method="post" action="/foreReviewOne" id="reviewOne">
                <div class="makeReviewText">其他买家，需要你的建议哦！</div>
                <table class="makeReviewTable">
                    <tr>
                        <td class="makeReviewTableFirstTD">评价商品</td>
                        <td><textarea name="content" id="content"></textarea></td>
                    </tr>
                </table>
                <div class="makeReviewButtonDiv">
                    <input type="hidden" name="pid" value="${oi.product.id}">
                    <input type="hidden" name="oiId" value="${oi.id}">
                    <button type="submit">提交评价</button>
                </div>
            </form>
        </div>
    </c:if>
</div>