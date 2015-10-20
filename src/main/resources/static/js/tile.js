function renderTile(xGridNum) {
    var i, j;
    var $tileArea = $('#tileArea');
    var $mapImage = $('#mapImage');
    var ratio = $mapImage.height() / $mapImage.width();
    var yGridNum = parseInt(xGridNum * ratio);
    var tileWidth = $mapImage.width() / xGridNum;
    var tileHeight = $mapImage.height() / yGridNum;

    $tileArea.empty();

    for (j = 0; j < yGridNum; j++) {
        var $tileRow = $('<div class="tileRow"></div>');
        $tileRow.appendTo($tileArea);
        for (i = 0; i < xGridNum; i++) {
            var $tile = $('<div class="tile" data-x="' + (i + 1) + '" data-y="' + (j + 1) + '"></div>');
            $tile.css({
                top: tileHeight * j,
                left: tileWidth * i,
                width: tileWidth,
                height: tileHeight
            });
            $tile.appendTo($tileRow);
        }
    }

}