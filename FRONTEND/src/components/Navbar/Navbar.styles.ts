import styled from 'styled-components';

export const StyledNavbar = styled.nav`
    display: flex;
    align-items: center;
    background-color: rgb(30, 30, 41) !important;
    justify-content: space-between;
    padding-left: 50px;
    padding-right: 50px;
    padding-top: 15px;
    padding-bottom: 15px;
`
export const StyledSubNav = styled.nav`
    display: flex;
    align-items: center;
    background-color: rgb(189, 189, 196) !important;
    justify-content: center;
    padding-left: 50px;
    padding-right: 50px;
    position: fixed;
    top: 0px;
    width: 100%;
`

export const StyledLogo = styled.a`
    color: white;
    font-size: 20px;
    text-decoration: none;
`

export const StyledLink = styled.a`
    color: black;
    font-size: 15px;
    text-decoration: none;
    font-weight: 500;
    padding-top: 5px;
    padding-bottom: 5px;
    padding-left: 10px;
    padding-right: 10px;
    
    &:hover {
        background-color: #a3a3a6;
    }
`

export const StyledSearchForm = styled.form`
    display: flex;
    width: 50%;
`

export const StyledSearchInput = styled.input`
    width: 200%;
    border-top-left-radius: 5px !important;
    border-bottom-left-radius: 5px !important;
    border-top-right-radius: 0px !important;
    border-bottom-right-radius: 0px !important;
`

export const StyledSearchButton = styled.button`
    display: flex;
    align-items: center;
    background-color: #ffc65b;
    border-top-right-radius: 5px !important;
    border-bottom-right-radius: 5px !important;
    border-bottom-left-radius: 0px !important;
    border-top-left-radius: 0px !important;
    border-color: #ffc65b;
    color: black;
    
    &:hover {
        background-color: #dd9d24;
        border-color: #dd9d24;
    }
    
    &:active {
        background-color: #dd9d24 !important;
        border-color: #dd9d24 !important;
    }
    
`
export const StyledButton = styled.button`
    background-color: #ffc65b;
    border-color: #ffc65b;
    color: black;
    font-weight: 500;
    
    &:hover{
        background-color: #dd9d24 !important;
        border-color: #dd9d24 !important;
        color: white;
    }

    &:active {
        background-color: #dd9d24 !important;
        border-color: #dd9d24 !important;
    }
    
`

export const StyledDropdownButton = styled.button`
    background-color: #ffc65b;
    border-color: #ffc65b;
    color: black;
    font-weight: 500;

    &:hover,
    &:active,
    &:focus,
    &:checked {
        background-color: #dd9d24 !important;
        border-color: #dd9d24 !important;
        color: white;
    }

    &:not(:focus):not(:checked):not(:active):not(:hover) {
        background-color: #ffc65b;
        border-color: #ffc65b;
        color: black;
    }
`