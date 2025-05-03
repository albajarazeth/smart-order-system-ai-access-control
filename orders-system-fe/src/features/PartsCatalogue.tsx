import "./PartsCatalogue.scss";
import droneParts from "../utilities/droneParts.json";
import { useState } from "react";
import { FaCopy } from "react-icons/fa";
import { Tooltip } from "react-tooltip";
import "react-tooltip/dist/react-tooltip.css";

const PartsCatalogue = () => {
  const [dronePartsList, setDronePartsList] = useState(droneParts);
  const [copiedIndex, setCopiedIndex] = useState<number | null>(null);

  const copyName = (partName: string, index: number) => {
    navigator.clipboard.writeText(partName);
    setCopiedIndex(index);
    setTimeout(() => setCopiedIndex(null), 2000); // Clear after 2s
  };

  return (
    <div>
      <h1>Parts Catalogue</h1>
      <div className="parts-catalogue">
        {dronePartsList.map((part, index) => {
          const isCopied = copiedIndex === index;

          return (
            <div key={index} className="card-container">
              <div className="card-body">
                <img className="imgage" src={part.imageUrl} />
                <div className="name-section">
                  <div className="part-name">{part.partName}</div>
                  <span
                    style={{ cursor: "pointer", position: "relative" }}
                    data-tooltip-id={!isCopied ? `tooltip-${index}` : undefined}
                    data-tooltip-content="Copy part name"
                  >
                    <FaCopy onClick={() => copyName(part.partName, index)} />
                    {isCopied && (
                      <span className="copied-feedback">Copied!</span>
                    )}
                  </span>
                  {!isCopied && <Tooltip id={`tooltip-${index}`} place="top" />}
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default PartsCatalogue;
