import {jwtDecode} from "jwt-decode";

// Interface để định nghĩa cấu trúc payload của token
interface DecodedToken {
  role: string; // Vai trò của người dùng (CUSTOMER, ADMIN, ...)
  sub: string;  // Tên hoặc ID người dùng
  iat: number;  // Thời gian phát hành token (issued at)
  exp: number;  // Thời gian hết hạn token (expiration time)
}

// Hàm giải mã JWT token và trả về payload chứa trong token
export const decodeToken = (token: string | null): DecodedToken | null => {
  if (!token) return null; // Kiểm tra nếu token là null

  try {
    // Giải mã token và trả về payload
    const decoded: DecodedToken = jwtDecode(token);
    return decoded;
  } catch (error) {
    console.error("Invalid token", error);
    return null;
  }
};
