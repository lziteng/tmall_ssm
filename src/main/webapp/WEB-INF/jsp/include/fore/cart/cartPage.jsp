<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    var deleteOrderItem = false;
    var deleteOrderItemid = 0;
    $(function () {
        $("a.deleteOrderItem").click(function () {
            deleteOrderItem = false;
            var oiid = $(this).attr("oiid")
            deleteOrderItemid = oiid;
            $("#deleteConfirmModal").modal('show');
        });
        $("button.deleteConfirmButton").click(function () {
            deleteOrderItem = true;
            $("#deleteConfirmModal").modal('hide');
        });

        //删除订单项
        $('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
            if (deleteOrderItem) {
                var page = "/foreDeleteOrderItem";
                $.post(
                    page,
                    {"oiid": deleteOrderItemid},
                    function (result) {
                        if ("success" == result) {
                            $("tr.cartProductItemTR[oiid=" + deleteOrderItemid + "]").hide();
                            calcCartSumPriceAndNumber();
                            syncCreateOrderButton();
                        } else {
                            location.href = "/loginPage";
                        }
                    }
                );
            }
        })

        //勾选时，切换图片和父节点tr的背景色
        $("img.cartProductItemIfSelected").click(function () {
            var selectit = $(this).attr("selectit");
            if ("selectit" == selectit) {
                $(this).attr("src", "img/site/cartNotSelected.png");
                $(this).attr("selectit", "false")
                $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
            } else {
                $(this).attr("src", "img/site/cartSelected.png");
                $(this).attr("selectit", "selectit");
                $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
            }
            syncAllSelect();
            syncCreateOrderButton();
            calcCartSumPriceAndNumber();
        });

        //全选按钮
        $("img.selectAllItem").click(function () {
            var selectit = $(this).attr("selectit");
            if ("selectit" == selectit) {
                $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
                $("img.selectAllItem").attr("selectit", "false");
                $(".cartProductItemIfSelected").each(function () {
                    $(this).attr("src", "img/site/cartNotSelected.png");
                    $(this).attr("selectit", "false");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
                });
            } else {
                $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
                $("img.selectAllItem").attr("selectit", "selectit");
                $(".cartProductItemIfSelected").each(function () {
                    $(this).attr("src", "img/site/cartSelected.png");
                    $(this).attr("selectit", "selectit");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
                });
            }
            syncCreateOrderButton();
            calcCartSumPriceAndNumber();
        });

        $(".orderItemNumberSetting").keyup(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();

            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            num = parseInt(num);
            if (isNaN(num))
                num = 1;
            if (num <= 0)
                num = 1;
            if (num > stock)
                num = stock;

            syncPrice(pid, num, price);
        });

        $(".numberPlus").click(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            num++;
            if (num > stock)
                num = stock;
            syncPrice(pid, num, price);
        });
        $(".numberMinus").click(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();

            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            --num;
            if (num <= 0)
                num = 1;
            syncPrice(pid, num, price);
        });

        $("button.createOrderButton").click(function () {
            var params = "";
            $(".cartProductItemIfSelected").each(function () {
                //需要排除父节点tr处于隐藏状态的情况
                if (!$(this).parent().parent().is(":hidden")) {
                    if ("selectit" == $(this).attr("selectit")) {
                        var oiid = $(this).attr("oiid");
                        params += "&oiid=" + oiid;
                    }
                }
            });
            params = params.substring(1);
            location.href = "/foreBuy?" + params;
        });
    });

    //判断结算按钮是否可点击
    function syncCreateOrderButton() {
        var selectAny = false;
        $(".cartProductItemIfSelected").each(function () {
            //需要排除父节点tr处于隐藏状态的情况
            if (!$(this).parent().parent().is(":hidden")) {
                if ("selectit" == $(this).attr("selectit")) {
                    selectAny = true;
                }
            }
        });

        if (selectAny) {
            $("button.createOrderButton").css("background-color", "#C40000");
            $("button.createOrderButton").removeAttr("disabled");
        } else {
            $("button.createOrderButton").css("background-color", "#AAAAAA");
            $("button.createOrderButton").attr("disabled", "disabled");
        }

    }

    //判断是否已全选
    function syncAllSelect() {
        var selectAll = true;
        $(".cartProductItemIfSelected").each(function () {
            //需要排除父节点tr处于隐藏状态的情况
            if (!$(this).parent().parent().is(":hidden")) {
                if ("false" == $(this).attr("selectit")) {
                    selectAll = false;
                }
            }
        });

        if (selectAll) {
            $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
        } else {
            $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
        }
    }

    //计算已选商品数量和总价
    function calcCartSumPriceAndNumber() {
        var sum = 0;
        var totalNumber = 0;
        $("img.cartProductItemIfSelected[selectit='selectit']").each(function () {
            //需要排除父节点tr处于隐藏状态的情况
            console.log($(this).parent().parent().is(":hidden"));
            if (!$(this).parent().parent().is(":hidden")) {
                var oiid = $(this).attr("oiid");
                var price = $(".cartProductItemSmallSumPrice[oiid=" + oiid + "]").text();
                price = price.replace(/,/g, "");
                price = price.replace(/￥/g, "");
                sum += new Number(price);
                var num = $(".orderItemNumberSetting[oiid=" + oiid + "]").val();
                totalNumber += new Number(num);
            }
        });

        $("span.cartSumPrice").html("￥" + formatMoney(sum));
        $("span.cartTitlePrice").html("￥" + formatMoney(sum));
        $("span.cartSumNumber").html(totalNumber);
    }

    function syncPrice(pid, num, price) {
        $(".orderItemNumberSetting[pid=" + pid + "]").val(num);
        var cartProductItemSmallSumPrice = formatMoney(num * price);
        $(".cartProductItemSmallSumPrice[pid=" + pid + "]").html("￥" + cartProductItemSmallSumPrice);
        calcCartSumPriceAndNumber();

        var page = "/foreChangeOrderItem";
        $.post(
            page,
            {"pid": pid, "number": num},
            function (result) {
                if ("success" != result) {
                    location.href = "/loginPage";
                }
            }
        );

    }
</script>

<title>购物车</title>
<div class="cartDiv">
    <div class="cartTitle pull-right">
        <span>已选商品  (不含运费)</span>
        <span class="cartTitlePrice">￥0.00</span>
        <button class="createOrderButton" disabled="disabled">结 算</button>
    </div>


    <div class="cartProductList">
        <table class="cartProductTable">
            <thead>
            <tr>
                <th class="selectAndImage">
                    <img selectit="false" class="selectAllItem" src="/img/site/cartNotSelected.png">
                    全选
                </th>
                <th>商品信息</th>
                <th>单价</th>
                <th>数量</th>
                <th width="120px">金额</th>
                <th class="operation">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ois }" var="oi">
                <tr oiid="${oi.id}" class="cartProductItemTR">
                    <td>
                        <img selectit="false" oiid="${oi.id}" class="cartProductItemIfSelected"
                             src="/img/site/cartNotSelected.png">
                        <a style="display:none" href="#nowhere"><img src="/img/site/cartSelected.png"></a>
                        <img class="cartProductImg"
                             src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg">
                    </td>
                    <td>
                        <div class="cartProductLinkOutDiv">
                            <a href="/foreProduct?pid=${oi.product.id}" class="cartProductLink">${oi.product.name}</a>
                            <div class="cartProductLinkInnerDiv">
                                <img src="/img/site/creditcard.png" title="支持信用卡支付">
                                <img src="/img/site/7day.png" title="消费者保障服务,承诺7天退货">
                                <img src="/img/site/promise.png" title="消费者保障服务,承诺如实描述">
                            </div>
                        </div>
                    </td>
                    <td>
                        <span class="cartProductItemOringalPrice">￥${oi.product.originalPrice}</span>
                        <span class="cartProductItemPromotionPrice">￥${oi.product.promotePrice}</span>
                    </td>
                    <td>

                        <div class="cartProductChangeNumberDiv">
                            <span class="hidden orderItemStock " pid="${oi.product.id}">${oi.product.stock}</span>
                            <span class="hidden orderItemPromotePrice "
                                  pid="${oi.product.id}">${oi.product.promotePrice}</span>
                            <a pid="${oi.product.id}" class="numberMinus" href="#nowhere">-</a>
                            <input pid="${oi.product.id}" oiid="${oi.id}" class="orderItemNumberSetting"
                                   autocomplete="off" value="${oi.number}">
                            <a stock="${oi.product.stock}" pid="${oi.product.id}" class="numberPlus"
                               href="#nowhere">+</a>
                        </div>
                    </td>
                    <td>
							<span class="cartProductItemSmallSumPrice" oiid="${oi.id}" pid="${oi.product.id}">
							￥<fmt:formatNumber type="number" value="${oi.product.promotePrice*oi.number}"
                                               maxFractionDigits="2"/>
							</span>
                    </td>
                    <td>
                        <a class="deleteOrderItem" oiid="${oi.id}" href="#nowhere">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="cartFoot">
        <img selectit="false" class="selectAllItem" src="/img/site/cartNotSelected.png">
        <span>全选</span>
        <!-- 		<a href="#">删除</a> -->
        <div class="pull-right">
            <span>已选商品 <span class="cartSumNumber">0</span> 件</span>
            <span>合计 (不含运费): </span>
            <span class="cartSumPrice">￥0.00</span>
            <button class="createOrderButton" disabled="disabled">结 算</button>
        </div>
    </div>
</div>