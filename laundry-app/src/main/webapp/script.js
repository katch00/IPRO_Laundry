function ggetMachines() {
    let location = document.getElementById('locationList').value;
    var pageTitle = document.getElementById('location-title');
    pageTitle.innerText = location;
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
