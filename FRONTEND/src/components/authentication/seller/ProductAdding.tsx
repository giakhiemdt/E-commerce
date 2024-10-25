// import React, { useEffect, useState } from "react";
// import styles from "../../../styles/ProductAdding.module.css";

// const ProductAdding: React.FC = () => {
//   const [name, setName] = useState<string>("");
//   const [quantity, setQuantity] = useState<number>(0);
//   const [price, setPrice] = useState<number>(0);
//   const [description, setDescription] = useState<string>("");
//   const [imageUrl, setImageUrl] = useState<string>("");

//     const fetchProductTypec = async () = {

//     };

//   return (
//     <div className={styles.page}>
//       <div className={`container ${styles.container}`}>
//         <h2 className={styles["admin-title"]}>Profile</h2> <br />
//         <div className="row">
//           <div className="col-12">
//             <h2 className={styles["small-title"]}>
//               Add Product
//               {/* {isSellerInfoStatus ? "Add Profile" : "Update Profile"} */}
//             </h2>
//             <div className={`${styles.paddingleft25} ${styles.paddingright25}`}>
//               <form
//                 onSubmit={(e) => {
//                   e.preventDefault();
//                   handleSubmit();
//                 }}
//               >
//                 <label>Name:</label>
//                 <input
//                   className={`form-control ${styles["input-bar"]}`}
//                   // value={isSellerInfoStatus ? "" : fullname}
//                   type="text"
//                   placeholder="Name"
//                   onChange={(e) => setName(e.target.value)}
//                 />
//                 <label>Quantity:</label>
//                 <input
//                   className={`form-control ${styles["input-bar"]}`}
//                   // value={isSellerInfoStatus ? "" : phone}
//                   type="text"
//                   placeholder="Quantity"
//                   onChange={(e) => setQuantity(parseInt(e.target.value))}
//                 />
//                 <label>Price:</label>
//                 <input
//                   className={`form-control ${styles["input-bar"]}`}
//                   // value={isSellerInfoStatus ? "" : address}
//                   type="text"
//                   placeholder="Price"
//                   onChange={(e) => setPrice(parseInt(e.target.value))}
//                 />
//                 <label>Description:</label>
//                 <input
//                   className={`form-control ${styles["input-bar"]}`}
//                   // value={isSellerInfoStatus ? "" : address}
//                   type="text"
//                   placeholder="Description"
//                   onChange={(e) => setDescription(e.target.value)}
//                 />
//                 <label>Image Url:</label>
//                 <input
//                   className={`form-control ${styles["input-bar"]}`}
//                   // value={isSellerInfoStatus ? "" : address}
//                   type="text"
//                   placeholder="Image Url"
//                   onChange={(e) => setImageUrl(e.target.value)}
//                 />

//                 <div className="d-grid">
//                   <button
//                     className={`btn btn-primary btn-block ${styles["btn-submit"]}`}
//                     type="submit"
//                   >
//                     {loading ? "Updating..." : "Update"}
//                   </button>
//                 </div>
//               </form>
//               {error && <p className={styles["error-text"]}>{error}</p>}
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default ProductAdding;
