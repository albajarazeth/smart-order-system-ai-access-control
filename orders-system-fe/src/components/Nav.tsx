import { MdOutlineSettingsSystemDaydream } from "react-icons/md";
import "./Nav.scss";

interface INav {
  name: string;
}

const Nav = (props: INav) => {
  const { name } = props;
  return (
    <nav>
      <ul>
        <li>
          <MdOutlineSettingsSystemDaydream size={50} />
        </li>
        <li>{name}</li>
      </ul>
    </nav>
  );
};

export default Nav;
