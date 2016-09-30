var id = 0;

$(document).ready(function(){
    getRandomQuote();
    
    $("#getRandomQuoteBtn").click(function(){
        
        $("#quoteOfToday").text(getRandomQuote());
    });
    
    $("#findQuote").keyup(function(){
        var input = $("#findQuote").val();
        console.log("input is: " + input);
        
        if(input !== "" && $.isNumeric(input)){
            
            $('#findQuoteButton').prop('dsiabled', false);
            
            $("#findQuoteButton").click(function(){
                
                getQuoteById();
            });
            
            
        } else {
            
            $('#findQuoteButton').prop('disabled', true);
        }
    });
    $("#createQuote").submit(function(event){
        
        event.preventDefault();
        createQuote();
    });
});

function createQuote(){
    
    var quote = new Object();
    quote.quote = $("#createQuote").find('input[name="createQuoteText"]').val();
    url = "http://localhost:9000/RestAJAX2/api/quote";
    
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(quote),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data){
            
            quote.quote = $("#createQuote").find('input[name="createQuoteText"]').val("");
            $("#error").attr("class", "alert alert-success");
            $("#error").show().html("Succesfully created");
        },
        
        fail: function(data) {
            
            var json = data.responseJSON;
            $("#error").attr("class", "alert alert-danger");
            $("#error").show().html(json.message);
        }
    });
}

function updateQuote(){
    
    var quote = new Object();
    quote.quote = $("#editQuote").find('input[name="updateQuoteText]').val();
    url = "http://localhost:9000/RestAJAX2/api/quote/" + id;
    $.ajax({
        
        url: url,
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(quote),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function(){
            
            $("#error").attr("class", "alert alert-success");
            $("#error").show().html("Succesfully updated");
        },
        error: function(data){
            
            var json = data.responseJSON;
            $("#error").attr("class", "alert alert-danger");
            $("#error").show().html(json.message);
        }
    });
}