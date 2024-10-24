import { create } from "zustand";

interface SellerState {
    isSellerInfoCompleted: boolean; // Trạng thái boolean
    setSellerInfoCompleted: (completed: boolean) => void; // Hàm để thay đổi trạng thái
}
  
const useSellerStore = create<SellerState>((set) => ({
    isSellerInfoCompleted: true, // Giá trị mặc định là chưa hoàn thành
    setSellerInfoCompleted: (completed) => set({ isSellerInfoCompleted: completed }), // Cập nhật trạng thái
}));

export default useSellerStore;

//Chưa biết sài cái gì