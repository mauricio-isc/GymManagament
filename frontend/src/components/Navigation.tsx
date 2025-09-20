import React from "react";
import {Link, useLocation} from 'react-router-dom'


const Navigation: React.FC = () => {
    const location = useLocation();

    return(
        <nav className="navigation">
            <div className="nav-brand">
                <h2>Gym Management</h2>
            </div>
            <ul className="nav-links">
                <li>
                    <Link to="/" className={location.pathname === '/' ? 'active' : ''}>
                        Dashboard
                    </Link>
                </li>
                <li>
                    <Link to="/members" className={location.pathname === '/members' ? 'active' : ''}>
                        Members
                    </Link>
                </li>
                <li>
                    <Link to="/memberships" className={location.pathname === '/memberships' ? 'active' : ''}>
                        Memberships
                    </Link>
                </li>
            </ul>
        </nav>
    );
};



export default Navigation;