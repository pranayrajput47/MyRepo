$(function () {
    $(".cmp-search__input").on("input", function () {
        let keywords = $(".cmp-search__input").val();

        if (keywords.length >= 3) {
            $.get("/bin/fetchKeyword?keyWord="+keywords)
                .done(function (data) {
                    alert("Data Loaded: " + data);
                });
        }
    });
});