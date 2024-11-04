import {AccountResponse} from './AccountResponse';

export interface AccountsResponse {
    ADMIN: AccountResponse[];
    SELLER: AccountResponse[];
    USER: AccountResponse[];
}

