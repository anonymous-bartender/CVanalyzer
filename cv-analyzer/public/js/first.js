function init() {
    $(myChart).css("display", "inline-block");
    $('form').css("display", "inline-block");
    $('#loader').css("display", "none");
    $('#extended').css("display", "none");

    $(document).ready(function() {
        $(".button-collapse").sideNav({
            menuWidth: 300, // Default is 240
            closeOnClick: true // Closes side-nav on <a> clicks, useful for Angular/Meteor
        });

        $('.parallax').parallax();
        $('.collapsible').collapsible();
        $('select').material_select();
    });
}


function reset() {
    location.reload();
}

function scrollTo(value) {
    $(value).css("display", "inline-block");
    $('html, body').animate({
        scrollTop: $(value).offset().top
    }, 'slow');
    return false;
}

function request(data, serverurl) {

    $('#loader').css("display", "inline-block");
    jQuery.ajax({
        url: serverurl,
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
        success: UploadSuccess,
        error: function(xhr) {

            $('#loader').css("display", "none");
            $('#error').css("display", "inline-block");
            $('#submit').removeClass("disabled");

            if (xhr.status === 405) {
                var x = JSON.parse(xhr.responseText);
                $('#error').text(x.Message);
            } else {
                var x = JSON.parse(xhr.responseText);
                $('#error').text("Error ocurred at Server side: " + x.Message);

            }
        }
    });

}




function showmore() {

    $('#extended').css("display", "block");
    scrollTo('#extended');
}

function UploadSuccess(inputjson) {

    upload("myChart", parseLabel(inputjson, "charset"), parseData(inputjson, "charset"));

    $('.progress').css("display", "none");
    $('#submit').css("display", "none");
    $('#reset').css("display", "inline-block");

    var topValues = inputjson.Top;
    if (topValues.Word.Count > 0) {

        var text = '<li class="collection-item"><span class="blue-text text-darken-2">' + topValues.Word.Value + '</span> is most used word. It is used exactly <span class="blue-text text-darken-2">' + topValues.Word.Count + ' times</span>.' + '</li>';
        $("#placeholder").append(text);
    }
    if (topValues.Verb.Count > 0) {
        var text = '<li class="collection-item"><span class="blue-text text-darken-2">' + topValues.Verb.Value + '</span> is most used verb with occurance of <span class="blue-text text-darken-2">' + topValues.Verb.Count + ' times</span>.' + '</li>';
        $("#placeholder").append(text);
    }
    if (topValues.Preposition.Count > 0) {
        var text = '<li class="collection-item"><span class="red-text text-darken-2">' + topValues.Preposition.Value + '</span> is used <span class="red-text text-darken-2">' + topValues.Preposition.Count + ' times.</span>' + '</li>';
        $("#placeholder").append(text);

    }
    if (topValues.Pronoun.Count > 0) {
        var text = '<li class="collection-item"><span class="teal-text text-darken-2">' + topValues.Pronoun.Value + '</span> is most used word. It is used exactly <span class="teal-text text-darken-2">' + topValues.Pronoun.Count + ' times. </span>' + '</li>';
        $("#placeholder").append(text);

    }

    $('#showmore').css("display", "inline-block");

}

function submit() {

    $('#submit').addClass("disabled");
    var data = new FormData(jQuery('form')[0]);
    request(data, '/cv-analyzer/upload');


}

function parseLabel(inputjson, pointerNode) {

    //console.log("parseLabel funtion ");
    //console.log(inputjson);
    var mainarray = inputjson[pointerNode];

    //var mainarray=inputjson.pointer;

    var output1 = [];
    var output2 = [];

    for (var key in mainarray) {
        var one = mainarray[key];
        for (item in one) {
            // console.log(one[item]);
            output1.push(item);
            output2.push(one[item]);
        }
    }

    var str = JSON.stringify(output1, null, 2); // spacing level = 2
    console.log(str);

    return output1;
}

function parseData(inputjson, pointerNode) {


    console.log("parseData funtion ");
    var mainarray = inputjson[pointerNode];

    //var mainarray=inputjson.pointer;

    var output1 = [];
    var output2 = [];

    for (var key in mainarray) {
        var one = mainarray[key];
        for (item in one) {
            // console.log(one[item]);
            output1.push(item);
            output2.push(one[item]);
        }
    }

    var str = JSON.stringify(output2, null, 2); // spacing level = 2
    console.log(str);

    return output2;

}

function upload(chartId, mylabels, mydata) {

    //console.log("upload funtion ");

    var ctx = document.getElementById(chartId);
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: mylabels,
            datasets: [{
                label: '# of Appearances',
                data: mydata,
                //backgroundColor: randomColorGenerator()
                backgroundColor: [
                    'rgba(0, 90, 211, 1)',
                    'rgba(0, 97, 211, 1)',
                    'rgba(0, 100, 211, 1)',
                    'rgba(0, 104, 211, 1)',
                    'rgba(0, 110, 211, 1)',
                    'rgba(0, 116, 211, 1)',
                    'rgba(0, 119, 211, 1)',
                    'rgba(0, 124, 211, 1)',
                    'rgba(0, 128, 211, 1)',
                    'rgba(0, 130, 211, 1)',
                    'rgba(0, 133, 211, 1)',
                    'rgba(0, 137, 211, 1)',
                    'rgba(0, 143, 211, 1)',
                    'rgba(0, 145, 211, 1)',
                    'rgba(0, 152, 211, 1)',
                    'rgba(0, 156, 211, 1)',
                    'rgba(0, 161, 211, 1)',
                    'rgba(0, 166, 211, 1)',
                    'rgba(0, 168, 211, 1)',
                    'rgba(0, 172, 211, 1)',
                    'rgba(0, 178, 211, 1)',
                    'rgba(0, 186, 211, 1)',
                    'rgba(0, 190, 211, 1)',
                    'rgba(0, 195, 211, 1)',
                    'rgba(0, 200, 211, 1)',
                    'rgba(0, 204, 211, 1)',
                    'rgba(0, 208, 211, 1)',
                    'rgba(0, 212, 211, 1)',
                    'rgba(0, 214, 211, 1)',
                    'rgba(0, 217, 211, 1)',
                    'rgba(0, 221, 211, 1)',
                    'rgba(0, 222, 211, 1)',
                    'rgba(0, 227, 211, 1)',
                    'rgba(0, 232, 211, 1)',
                    'rgba(0, 234, 211, 1)',
                    'rgba(0, 239, 211, 1)',
                    'rgba(0, 242, 211, 1)'
                ]
                /*             borderColor: [
                                'rgba(255,99,132,1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1 */
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

}

var randomColorGenerator = function() {
    var s = '#' + (Math.random().toString(16) + '0000000').slice(2, 8);
    console.log(s);
    return s;
};
