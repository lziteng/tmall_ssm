Partial = {
    refresh: function (url, component, data) {
        var refreshSelector = "#" + component;
        var refresh = null;
        var c = $(refreshSelector);
        while (c && c.parentNode && c.parentNode.nodeName != "BODY")
        {
            if (c.type == "refresh" || c.getAttribute("type") == "refresh")
            {
                refresh = c.id;
                break;
            }
            c = c.parentNode;
        }
        $.ajax({
            type: "post",
            dataType: "html",
            url: url,
            data: data,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("refresh", refresh);
                xhr.innerHTML = "<div class='loading'>正在加载数据...</div>";
            },
            success: function (result) {
                result = $.parseHTML(result, document, true);
                var newContent = $(result).find(refreshSelector);
                $(refreshSelector).html(newContent.html());
            }
        })
    }
};