var washerCounter = 0;
var dryerCounter = 0;
var timer;
function ggetMachines() {
    let location = document.getElementById('locationList').value;
    var pageTitle = document.getElementById('location-title');
    pageTitle.innerText = location;
    
    var exist = document.getElementById('refresh-div');
    if (exist == null) {
        var refreshDiv = document.createElement('div');
        refreshDiv.className = 'tm-wrapper-center';
        refreshDiv.setAttribute("id", "refresh-div");
        
        var washerAvail = document.createElement('h5');
        var dryerAvail = document.createElement('h5');
        washerAvail.setAttribute("id", "washerAvail");
        dryerAvail.setAttribute("id", "dryerAvail");
        refreshDiv.appendChild(washerAvail);
        refreshDiv.appendChild(dryerAvail);
        
        var refreshBtn = document.createElement('button');
        refreshBtn.innerText = "Refresh"; 
        refreshBtn.className = 'tm-btn-refresh';
        refreshBtn.setAttribute("type", "button");
        refreshBtn.setAttribute("onClick", "ggetMachines()");
        refreshDiv.appendChild(refreshBtn);

        pageTitle.parentNode.insertBefore(refreshDiv,pageTitle.nextSibling);
    }

    exist = document.getElementById('avail-div');
    if(exist != null) {
        var rb = document.getElementById('quick-view-div');
        rb.removeChild(rb.children[1]);
    }

    washerCounter = 0;
    dryerCounter = 0;
    fetch(`/machines?locationList=${location}`).then(response => response.json()).then((messages) => {
    var gridEl = document.getElementById('machine-container');
    while( gridEl.firstChild ){
        gridEl.removeChild( gridEl.firstChild );
    }
    var count = 0;
    var row1 = document.createElement('div'); row1.className = 'row';
    var row2 = document.createElement('div'); row2.className = 'row';
    var row3 = document.createElement('div'); row3.className = 'row';
    messages.forEach((message) => {
        count++;
        const colEl = document.createElement('div');
        colEl.className = 'col-sm-3';
	colEl.appendChild(createMachineCard(message));
        if(count <5)
        {
            row1.appendChild(colEl);            
        }
        if(count < 9){
            row2.appendChild(colEl);
        }
        else
        {
            row3.appendChild(colEl);
        }
    })
    gridEl.appendChild(row1);
    gridEl.appendChild(row2);
    gridEl.appendChild(row3);    
    displayNumAvailable();
    displayTimer();
  });
}


function createMachineElement(machine) {
  const machineElement = document.createElement('li');
  machineElement.className = 'machine';

  const titleElement = document.createElement('span');
  titleElement.innerText = machine.testing;

  machineElement.appendChild(titleElement);
  
  return machineElement;
}

function createMachineCard(machine) {
    const machineCard = document.createElement('div');
    machineCard.className = 'card bg-card card-block d-flex text-md-center';

    const cardImg = document.createElement('img');
    cardImg.className = 'cardIcon center-block';    
    var status = machine.status;
    var type = machine.type;
    
    const cardSubTitle = document.createElement('h4');
    cardSubTitle.innerText = status;
    
    var time = machine.timeRemaining;
    const cardTime = document.createElement('h6');
    if(status == "busy") {
      cardTime.innerText = time.toString() + " min remaining"; 
      cardTime.className = 'card-time';
    }
    else {
      cardTime.innerText = "-";
      cardTime.className = 'card-time text-empty';
    }
    

    if(type == "dryer") //offline, idle, busy, unknown 
    {
        if(status == "offline" || status == "unknown")
        {
            cardImg.src = 'img/offlineDryer.png';
            cardSubTitle.className = 'card-subtitle text-muted';
        }
        else if(status == "idle")
        {
            dryerCounter++;
            cardImg.src = 'img/greenDryer.png';
            cardSubTitle.className = 'card-subtitle text-success';
        }
        else if(status == "busy")
        {
            cardImg.src = 'img/redDryer.png';
            cardSubTitle.className = 'card-subtitle text-danger';
        }
        
    }
    else if(type == "washer"){
	
	    
        if(status == "offline" || status == "unknown")
        {
            cardImg.src = 'img/offlineWasher.png';
            cardSubTitle.className = 'card-subtitle text-muted';
        }
        else if(status == "idle")
        {
            washerCounter++;
            cardImg.src = 'img/greenWasher.png';
            cardSubTitle.className = 'card-subtitle text-success';
        }
        else if(status == "busy")
        {
            cardImg.src = 'img/redWasher.png';
            cardSubTitle.className = 'card-subtitle text-danger';
            
        }
    }
  
   
    const cardBody = document.createElement('div');
    cardBody.className = 'card-body';

    const cardTitle = document.createElement('h2');
    cardTitle.className = 'card-title';
    cardTitle.innerText = machine.name;

    machineCard.appendChild(cardImg);
    machineCard.appendChild(cardBody);
    machineCard.appendChild(cardTitle);
    machineCard.appendChild(cardSubTitle);
    machineCard.appendChild(cardTime);
    if(status == "busy")
    {
        const notifyMe = document.createElement('button');
        notifyMe.type = 'button';
        notifyMe.className = 'btn btn-info btn-sm';
        notifyMe.setAttribute('data-toggle', 'modal');
        notifyMe.setAttribute('data-target', '#myModal');
        notifyMe.innerText = 'Notify me!';
        machineCard.appendChild(notifyMe);
    }
    else
    {
        const formatButton = document.createElement('button');
        formatButton.className = 'btn btn-sm invisible';
        machineCard.appendChild(formatButton);
    }
    
    return machineCard;
}

function displayNumAvailable() {
   var washerAvail = document.getElementById('washerAvail');
   var dryerAvail = document.getElementById('dryerAvail');
   washerAvail.innerText = "Washers: " + washerCounter + " available";
   dryerAvail.innerText = "Dryers: " + dryerCounter + " available";
}
function displayTimer() {
        
    var refreshDiv = document.getElementById('refresh-div');
    var countdown = document.getElementById('countdown');

    if(countdown==null) {
        countdown = document.createElement('h6');
        countdown.setAttribute('id', 'countdown');
        refreshDiv.appendChild(countdown);
    }

    if(timer != null) {
        clearInterval(timer);
        timer = null;
        countdown.innerText = "";
    }

    var seconds = 30;
    timer = setInterval(function() {
        countdown.innerText = "Refreshing in " +  seconds + " seconds ";
        seconds--;
         if (seconds < 0) {
            seconds = 30;
            try{
                ggetMachines();
            }
            catch(err) {
                console.log(err.message);
                clearInterval(timer);
            }
        }
    }, 1000);
}

function quickView(){
    var exist = document.getElementById('avail-div');
    if (exist == null) {        
    	var availDiv = document.createElement('div');
        availDiv.setAttribute("id", "avail-div");
        
        fetch(`/machines?quick=true`).then(response => response.json()).then((messages) => {
            messages.forEach((message) => {
                var loc = document.createElement('h5');
                loc.className = 'tm-section-avail';
                loc.innerText = message.location + " - " + message.washerAvail + " washers and " + message.dryerAvail + " dryers are available";
                availDiv.appendChild(loc);
            })
        });
        var quickView = document.getElementById('quick-view-btn');
        quickView.parentNode.insertBefore(availDiv,quickView.nextSibling);
    }
    else {
        fetch(`/machines?quick=true`).then(response => response.json()).then((messages) => {
            messages.forEach((message) => {
                var availDiv = document.getElementById('avail-div');
                availDiv.removeChild(availDiv.firstChild);
                var loc = document.createElement('h5');
                loc.className = 'tm-section-avail';
                loc.innerText = message.location + " - " + message.washerAvail + " washers and " + message.dryerAvail + " dryers are available";
                availDiv.appendChild(loc);
            })
        });
    }
}