"use strict";
$(function () {
  // 動的に複数のボタンに対応
  $(document).on("click", ".goodButton", function () {
    const articleId = $(this).data("article-id");

    $.ajax({
      url: "/article/add-good",
      type: "POST",
      data: { articleId: articleId },
      dataType: "json",
    })
      .done(function (data) {
        // 該当の記事にのみ反映
        $("#goodCount-" + data.articleId).text("いいね数: " + data.goodCount);
      })
      .fail(function () {
        alert("エラーが発生しました！");
      });
  });
});
