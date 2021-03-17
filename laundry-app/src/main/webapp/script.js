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
    machineCard.className = 'card';


    const cardImg = document.createElement('img');
    cardImg.className = 'cardIcon center-block';    
    var status = machine.status;
    var type = machine.type;
    if(type == "dryer") //offline, idle, busy, unknown 
    {
        if(status == "offline" || status == "unknown")
        {
            cardImg.src = 'img/offlineDryer.png';
        }
        else if(status == "idle")
        {
            cardImg.src = 'img/greenDryer.png';
        }
        else if(status == "busy")
        {
            cardImg.src = 'img/redDryer.png';
        }
        
    }
    else if(type == "washer"){
        if(status == "offline" || status == "unknown")
        {
            cardImg.src = 'img/offlineWasher.png';
        }
        else if(status == "idle")
        {
            cardImg.src = 'img/greenWasher.png';
        }
        else if(status == "busy")
        {
            cardImg.src = 'img/redWasher.png';
        }
    }
    
    

    const cardBody = document.createElement('div');
    cardBody.className = 'card-body';

    const cardTitle = document.createElement('h5');
    cardTitle.className = 'card-title';
    cardTitle.innerText = machine.status;
    const cardSubTitle = document.createElement('h6');
    cardSubTitle.className = 'card-subtitle text-muted';
    cardSubTitle.innerText = machine.location;
    
    const cardText = document.createElement('p');
    cardText.className = 'card-text';
    cardText.innerText = machine.testing;

    machineCard.appendChild(cardImg);
    machineCard.appendChild(cardBody);
    machineCard.appendChild(cardTitle);
    machineCard.appendChild(cardSubTitle);
    machineCard.appendChild(cardText);
    
    return machineCard;
}