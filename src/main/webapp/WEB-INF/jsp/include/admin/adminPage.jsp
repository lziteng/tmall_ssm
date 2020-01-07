<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("ul.pagination li.disabled a").click(function () {
            return false;
        });
    });
</script>
<nav>
    <ul class="pagination">
        <li
                <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>
                <c:if test="${page.hasPreviouse}">title="第一页"</c:if>
        >
            <a start="0" param="${page.param}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>

        <li
                <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>
                <c:if test="${page.hasPreviouse}">title="上一页"</c:if>
        >
            <a start="${page.start-page.count}" param="${page.param}" aria-label="Previous">
                <span aria-hidden="true">&lsaquo;</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
            <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                <a start="${status.index*page.count}" param="${page.param}"
                   <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
                >${status.count}</a>
            </li>
        </c:forEach>

        <li
                <c:if test="${!page.hasNext}">class="disabled"</c:if>
                <c:if test="${page.hasNext}">title="下一页"</c:if>
        >
            <a start="${page.start+page.count}" param="${page.param}" aria-label="Next">
                <span aria-hidden="true">&rsaquo;</span>
            </a>
        </li>
        <li
                <c:if test="${!page.hasNext}">class="disabled"</c:if>
                <c:if test="${page.hasNext}">title="最后一页"</c:if>
        >
            <a start="${page.last}" param="${page.param}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>

    <ul class="pagination">
        <li>
            <a>
                每页${page.count}条,共${page.total}条
            </a>
        </li>
    </ul>
</nav>
