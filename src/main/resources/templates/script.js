document.getElementById("getRootUrlButton").addEventListener("click", function(){
    const outputDiv = document.getElementById("output");

    const apiUrl = "http://localhost:8080/";

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            // Handle the data returned from the API
            const outputDiv = document.getElementById("output");
            outputDiv.textContent = "Root URL: ";
        })
        .catch(error => {
            console.error("Error:", error);
            outputDiv.textContent = "An error occurred while fetching data.";
        });
});