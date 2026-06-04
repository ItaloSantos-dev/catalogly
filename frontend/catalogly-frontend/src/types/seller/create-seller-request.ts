import { RegisterRequestDTO } from "../auth/register-request";

export interface CreateSellerRequestDTO {
    userData:RegisterRequestDTO;
    phone:string;
}