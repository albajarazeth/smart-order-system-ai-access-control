import "./PartsCatalogue.scss";
import droneParts from "../utilities/droneParts.json";
import { useState } from "react";

const PartsCatalogue = () => {
  const [dronePartsList, setDronePartsList] = useState(droneParts);
  return (
    <div>
      <h1>Parts Catalogue</h1>
      <div className="parts-catalogue">
        {dronePartsList.map((part, index) => {
          return (
            <div key={index} className="card-container">
              <div className="card-body">
                <img className="imgage" src={part.image_url} />
                <div className="part-name">{part.name}</div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
export default PartsCatalogue;
