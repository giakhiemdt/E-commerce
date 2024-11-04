import React, { useEffect } from "react";
import { IoSearch } from "react-icons/io5";
import { MdOutlineLogin } from "react-icons/md";
import { VscAdd } from "react-icons/vsc";
import { MdOutlineAccountBox } from "react-icons/md";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../utils/AuthContext"
import { useLogout } from "../../hooks/useAuth"

import {StyledNavbar, StyledLogo, StyledSearchForm,
    StyledSearchInput, StyledSearchButton, StyledButton,
    StyledSubNav, StyledLink, StyledDropdownButton} from "./Navbar.styles"

const Navbar: React.FC = () => {

    const navigate = useNavigate();
    const { decodedToken } = useAuth();
    const logout = useLogout();

    useEffect(() => {
        const adjustNavbarPosition = () => {
            const navbar = document.querySelector('.navbar') as HTMLElement | null;
            const subnav = document.querySelector('.subnav') as HTMLElement | null;

            if (navbar && subnav) {
                const navbarHeight = navbar.offsetHeight;
                if (window.scrollY < navbarHeight) {
                    subnav.style.top = navbarHeight - window.scrollY + 'px';
                } else {
                    subnav.style.top = `0px`;
                }
            }
        };

        adjustNavbarPosition();
        window.addEventListener('scroll', adjustNavbarPosition);
        window.addEventListener('resize', adjustNavbarPosition);

        return () => {
            window.removeEventListener('scroll', adjustNavbarPosition);
            window.removeEventListener('resize', adjustNavbarPosition);
        };
    }, []);

    const handleLogout = async () => {
        await logout();
        window.location.replace("/home");
    }

    return (
        <div style={{marginBottom: '32.5px'}}>
            <StyledNavbar className={'navbar'}>
                <StyledLogo onClick={() => navigate('/home')}>MuaVN</StyledLogo>
                <StyledSearchForm method={'POST'} action={'/'}>
                    <StyledSearchInput className={'form-control'} placeholder={'Search'} type='text'></StyledSearchInput>
                    <StyledSearchButton className={'btn btn-primary'} type="submit">
                        <IoSearch/>
                    </StyledSearchButton>
                </StyledSearchForm>
                {decodedToken ? (
                    <>
                        <div className="dropdown">
                            <StyledDropdownButton type="button" className="btn btn-primary dropdown-toggle"
                                                  data-bs-toggle="dropdown">
                                <MdOutlineAccountBox style={{fontSize: '30px'}}/>
                            </StyledDropdownButton>
                            <ul className="dropdown-menu" style={{zIndex: '1500', left: 'auto', right: '0'}}>
                                <li><a className="dropdown-item" onClick={() => navigate('/account')}>Account</a></li>
                                {decodedToken.role === "ADMIN" ? (
                                    <>
                                        <li><a className="dropdown-item" onClick={() => navigate('/manage')}>Manage</a></li>
                                    </>
                                ) : decodedToken.role === "SELLER" ? (
                                    <>
                                        <li><a className="dropdown-item" href="#">My Product</a></li>
                                        <li><a className="dropdown-item" href="#">My Order</a></li>
                                        <li><a className="dropdown-item" href="#">Support</a></li>
                                    </>
                                ) : decodedToken.role === "USER" ? (
                                    <>
                                        <li><a className="dropdown-item" href="#">My Cart</a></li>
                                        <li><a className="dropdown-item" href="#">My Order</a></li>
                                    </>
                                ) : null}
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li><a className="dropdown-item" onClick={() => handleLogout()}>Logout</a></li>
                            </ul>
                        </div>
                    </>
                ) : (
                    <>
                        <div>
                            <StyledButton onClick={() => navigate("/login")} className={'btn btn-primary'}
                                          style={{marginRight: '5px'}} type={'submit'}>
                                <MdOutlineLogin style={{marginRight: '3px'}}/>
                                Login</StyledButton>
                            <StyledButton onClick={() => navigate("/register")} className={'btn btn-primary'}
                                          type={'submit'}>
                                <VscAdd style={{marginRight: '3px'}}/>
                                Register</StyledButton>
                        </div>
                    </>
                )}


            </StyledNavbar>
            <StyledSubNav className={'subnav'}>
                <StyledLink href={'/'}>Top</StyledLink>
                <StyledLink href={'/'}>Today Sale</StyledLink>
                <StyledLink href={'/'}>Follow</StyledLink>
                <StyledLink href={'/'}>Category</StyledLink>
                <StyledLink href={'/'}>About us</StyledLink>
            </StyledSubNav>
        </div>
    );
}

export default Navbar;


